package com.turygin.servlet;

import com.turygin.api.client.RestClient;
import com.turygin.utility.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.auth0.jwk.*;

/**
 *  Application startup servlet that initializes REST Client and Cognito JWK provider.
 */
@WebServlet(
    name = "WebAppStartup",
    urlPatterns = { "/webAppStartup"},
    loadOnStartup = 1
)
public class WebAppStartup extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(WebAppStartup.class);

    /** Empty constructor. */
    public WebAppStartup() {}

    /**
     * Creates a REST API Client and Cognito JWK Provider and places them into ServletContext for other servlets to use.
     * @throws ServletException if unable to initialize servlet
     */
    public void init() throws ServletException {
        super.init();
        ServletContext context = getServletContext();

        Properties webAppProps = Config.getProperties();
        String apiBaseUrl = webAppProps.getProperty("rest.client.baseUrl");

        RestClient restClient = new RestClient(apiBaseUrl);
        context.setAttribute("restClient", restClient);
        LOG.debug("Initialized REST API client with base URL {}.", apiBaseUrl);

        String issuerUrl = Config.getCognitoIssuerUrl();
        JwkProvider cognitoJwkProvider = new JwkProviderBuilder(issuerUrl)
                .cached(10, 24, TimeUnit.HOURS) // Cache up to 10 keys for 24 hours
                .rateLimited(10, 1, TimeUnit.MINUTES) // Rate limit requests to 10 per minute
                .build();
        context.setAttribute("cognitoJwkProvider", cognitoJwkProvider);
        LOG.debug("Initialized Cognito JWK Provider from URL {}.", issuerUrl);
    }

}

