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


/**
 * Servlet that selects a course in cart and displays section information.
 */
@WebServlet(
        name = "CartSelectCourse",
        urlPatterns = { "/cart/select" }
)
public class SelectCourse extends HttpServlet {

    /** Empty constructor. */
    public SelectCourse() {}

    /**
     * Handles HTTP GET requests which contains course list ID as a query parameter.
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

        // Web filter ensures that page state was initialized.
        ViewCartPageState pageState = (ViewCartPageState) session.getAttribute("viewCartPage");

        try {
            // Select a course from the list if the index is valid ...
            int courseListId = Integer.parseInt(request.getParameter("courseListId"));
            pageState.getCourses().setSelectedId(courseListId);
        } catch (Exception e) {
            // ... or remove selection
            pageState.getCourses().resetSelected();
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(navState.getJspPage());
        dispatcher.forward(request, response);
    }
}
