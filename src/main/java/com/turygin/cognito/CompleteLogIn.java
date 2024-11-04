package com.turygin.cognito;

import com.auth0.jwk.InvalidPublicKeyException;
import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.turygin.api.client.RestClient;
import com.turygin.api.model.UserDTO;
import com.turygin.states.UserState;
import com.turygin.utility.Config;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;


@WebServlet(
        urlPatterns = {"/completeLogIn"}
)
public class CompleteLogIn extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(CompleteLogIn.class);

    /**
     * Gets the auth code from the request and exchanges it for a token containing user info.
     * @param request servlet request
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletContext context = getServletContext();
        RestClient client = (RestClient) context.getAttribute("restClient");
        JwkProvider cognitoJwkProvider = (JwkProvider) context.getAttribute("cognitoJwkProvider");

        HttpSession session = request.getSession();
        UserState userState = (UserState) session.getAttribute("userState");

        String authorizationCode = request.getParameter("code");

        // Check for missing authorization code or if user already logged in
        if (authorizationCode == null || userState != null) {
            response.sendRedirect(String.format("%s/%s", request.getContextPath(), "browseCoursesLoadList"));
            return;
        }
        TokenResponse token = client.getCognitoToken(authorizationCode);

        try {
            DecodedJWT jwt = validateToken(token, cognitoJwkProvider);
            assert jwt != null;

            userState = getJwtClaims(jwt);
            assert userState != null;

            populateUserFromDatabase(userState, client);
            session.setAttribute("userState", userState);
            LOG.debug("Successfully authenticated user with UUID '{}' and ID '{}'.",
                    userState.getCognitoUuid().toString(), userState.getUserId());
        } catch (AssertionError assertionError) {
            // Ignore
        } catch (Exception e) {
            LOG.error("Authorization failed!", e);
        }

        response.sendRedirect(String.format("%s/%s", request.getContextPath(), "browseCoursesLoadList"));
    }

    private DecodedJWT validateToken(TokenResponse tokenResponse, JwkProvider provider) {
        try {
            // Decode the token to get the key ID (kid)
            DecodedJWT jwt = JWT.decode(tokenResponse.getIdToken());
            String kid = jwt.getKeyId();

            // Get the JWK from the provider using the kid
            Jwk jwk = provider.get(kid);

            // Extract the public key from the JWK
            RSAPublicKey publicKey = (RSAPublicKey) jwk.getPublicKey();

            // Define the RSA256 algorithm with the public key
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);

            // Set up the JWT verifier with the issuer and algorithm
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(Config.getCognitoIssuerUrl())
                    .build();

            // Verify the token
            verifier.verify(jwt);
            return jwt;
        } catch (JWTDecodeException jwtDecodeException) {
            LOG.error("Could not decode JWT!", jwtDecodeException);
        } catch (InvalidPublicKeyException invalidPublicKeyException) {
            LOG.error("Could not get JWK public key!", invalidPublicKeyException);
        } catch (JWTVerificationException jwtVerificationException) {
            LOG.error("JWT token is invalid!", jwtVerificationException);
        } catch (JwkException jwkException) {
            LOG.error("Could not load key from JWK provider!", jwkException);
        } catch (Exception e) {
            LOG.error("Something went wrong during JWT verification!", e);
        }

        return null;
    }

    private UserState getJwtClaims(DecodedJWT jwt) {
        try {
            UserState userState = new UserState();

            String claim = jwt.getClaim("cognito:username").asString();
            assert claim != null;
            userState.setUserName(claim);

            claim = jwt.getClaim("given_name").asString();
            assert claim != null;
            userState.setFirstName(claim);

            claim = jwt.getClaim("family_name").asString();
            assert claim != null;
            userState.setLastName(claim);

            claim = jwt.getClaim("sub").asString();
            assert claim != null;
            userState.setCognitoUuid(UUID.fromString(claim));

            return userState;
        } catch (IllegalArgumentException illegalArgumentException) {
            LOG.error("Could not Cognito UUID!", illegalArgumentException);
        } catch (AssertionError assertionError) {
            LOG.error("One of the claims is missing!", assertionError);
        } catch (Exception e) {
            LOG.error("Something went wrong when parsing JWT claims!", e);
        }

        return null;
    }

    private void populateUserFromDatabase(UserState userState, RestClient restClient) {
        UserDTO userDto = restClient.createUserIfNotExists(userState.getCognitoUuid().toString());
        userState.setIsAdmin(userDto.isAdmin());
        userState.setIsNew(userDto.isNewUser());
        userState.setUserId(userDto.getId());
    }
}

