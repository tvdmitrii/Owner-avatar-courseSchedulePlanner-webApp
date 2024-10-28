package com.turygin.cognito;

import com.turygin.utility.Config;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Properties;
import java.io.IOException;

/**
 * Initiates authentication process with AWS Cognito.
 */
@WebServlet(
        urlPatterns = {"/logIn"}
)
public class LogIn extends HttpServlet {
    /**
     * Route to the aws-hosted cognito login page.
     * @param request servlet request
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Properties webAppProps = Config.getProperties();
        String cognitoUrl = String.format("%s?response_type=code&client_id=%s&redirect_uri=%s",
                webAppProps.getProperty("cognito.loginURL"), webAppProps.getProperty("cognito.client.id"),
                webAppProps.getProperty("cognito.redirectURL"));
        response.sendRedirect(cognitoUrl);
    }
}
