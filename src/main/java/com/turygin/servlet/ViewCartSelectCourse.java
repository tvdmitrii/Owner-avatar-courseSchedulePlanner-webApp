package com.turygin.servlet;

import com.turygin.states.NavigationState;
import com.turygin.states.UserState;
import com.turygin.states.ViewCartPageState;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;



@WebServlet(
        name = "ViewCartSelectCourse",
        urlPatterns = { "/viewCartSelectCourse" }
)
public class ViewCartSelectCourse extends HttpServlet {

    private static final String JSP_URL = "/viewCart.jsp";

    /** Empty constructor. */
    public ViewCartSelectCourse() {}

    /**
     * Handles HTTP GET requests.
     * @param request object that contains the client's request information
     * @param response object used to send the response back to the client
     * @throws ServletException if servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Check that logged in.
        UserState user = (UserState) session.getAttribute("userState");
        if (user == null) {
            // Not logged in.
            response.sendRedirect(String.format("%s/%s", request.getContextPath(), "browseCoursesLoadList"));
            return;
        }

        // Set navigation state
        NavigationState navState = new NavigationState("viewCart");
        session.setAttribute("navState", navState);

        // Get page state
        ViewCartPageState pageState = (ViewCartPageState) session.getAttribute("viewCartPage");

        // Check that cart course list was initialized.
        if (pageState == null) {
            response.sendRedirect(String.format("%s/%s", request.getContextPath(), "viewCart"));
            return;
        }

        try {
            // Select a course from the list if the index is valid ...
            int courseListId = Integer.parseInt(request.getParameter("courseListId"));
            pageState.setSelectedCourseId(courseListId);
        } catch (Exception e) {
            // ... or remove selection
            pageState.setSelectedCourseId(-1);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_URL);
        dispatcher.forward(request, response);
    }
}
