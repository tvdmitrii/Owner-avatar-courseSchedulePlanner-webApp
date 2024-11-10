package com.turygin.cognito;

import com.turygin.states.nav.NavigationState;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

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

        HttpSession session = request.getSession();
        session.setAttribute("userState", null);
        response.sendRedirect(String.format("%s/%s", request.getContextPath(), NavigationState.HOME));
    }
}
