package com.turygin.servlet.cart;

import com.turygin.states.nav.NavigationState;
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
        name = "CartSelectCourse",
        urlPatterns = { "/cart/select" }
)
public class SelectCourse extends HttpServlet {

    /** Empty constructor. */
    public SelectCourse() {}

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

        // Set navigation state
        NavigationState navState = NavigationState.CART;
        session.setAttribute("navState", navState);

        // Check that logged in.
        UserState user = (UserState) session.getAttribute("userState");
        if (user == null) {
            // Not logged in.
            response.sendRedirect(String.format("%s/%s", request.getContextPath(), NavigationState.HOME));
            return;
        }

        // Get page state
        ViewCartPageState pageState = (ViewCartPageState) session.getAttribute("viewCartPage");

        // Check that cart course list was initialized.
        if (pageState == null) {
            response.sendRedirect(String.format("%s/%s", request.getContextPath(), navState.getDefaultServlet()));
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

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(navState.getJspPage());
        dispatcher.forward(request, response);
    }
}
