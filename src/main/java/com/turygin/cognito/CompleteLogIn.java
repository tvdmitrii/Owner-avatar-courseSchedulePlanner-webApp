package com.turygin.cognito;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.turygin.api.client.RestClient;
import com.turygin.utility.Config;
import jakarta.servlet.ServletContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.interfaces.RSAPublicKey;


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
        String authorizationCode = request.getParameter("code");

        // Fetch REST API client from the context
        ServletContext context = getServletContext();
        RestClient client = (RestClient) context.getAttribute("restClient");
        JwkProvider cognitoJwkProvider = (JwkProvider) context.getAttribute("cognitoJwkProvider");

        TokenResponse token = client.getCognitoToken(authorizationCode);
        boolean isTokenValid = validateToken(token, cognitoJwkProvider);
        LOG.debug("Token is valid: {}", isTokenValid);
    }

    private boolean validateToken(TokenResponse tokenResponse, JwkProvider provider) {
        boolean valid = false;

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
            valid = true;

            String userName = jwt.getClaim("cognito:username").asString();
            LOG.debug("here's the username: " + userName);
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
            valid = false;
        }

        return valid;
    }
}

