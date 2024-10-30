package com.turygin.cognito;

import com.auth0.jwk.JwkProvider;
import com.turygin.api.client.RestClient;
import com.turygin.states.UserState;
import com.turygin.utility.Config;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Properties;

/**
 * Logs the user out.
 */
@WebServlet(
        urlPatterns = {"/logOut"}
)
public class LogOut extends HttpServlet {
    /**
     * Route to the aws-hosted cognito login page.
     * @param request servlet request
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletContext context = getServletContext();
        context.setAttribute("userState", null);
        response.sendRedirect(String.format("%s/%s", request.getContextPath(), "browseCoursesLoadList"));
    }
}
