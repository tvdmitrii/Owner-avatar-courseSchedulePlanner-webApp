package com.turygin.servlet;

import com.turygin.api.client.RestClient;
import com.turygin.utility.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

/**
 *  Application startup servlet that initializes REST Client.
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
     * Creates a REST API Client and places it into ServletContext for other servlets to use.
     * @throws ServletException if unable to initialize servlet
     */
    public void init() throws ServletException {
        super.init();
        ServletContext context = getServletContext();
        String apiBaseUrl = Config.getProperties().getProperty("rest.client.baseUrl");
        context.setAttribute("restClient", new RestClient(apiBaseUrl));
        LOG.info("Initialized REST API client with base URL {}.", apiBaseUrl);
    }

}

