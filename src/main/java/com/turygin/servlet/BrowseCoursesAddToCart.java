package com.turygin.servlet;

import com.turygin.api.client.RestClient;
import com.turygin.states.BrowseCoursesPageState;
import com.turygin.states.NavigationState;
import com.turygin.states.UserState;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


/**
 * Sample servlet that fetches information about all courses form the
 * REST API and sends it to JSP for display.
 */
@WebServlet(
        name = "BrowseCoursesAddToCart",
        urlPatterns = { "/browseCoursesAddToCart" }
)
public class BrowseCoursesAddToCart extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(BrowseCoursesAddToCart.class);
    private static final String JSP_URL = "/browseCourses.jsp";

    /** Empty constructor. */
    public BrowseCoursesAddToCart() {}

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        // Check that logged in.
        UserState user = (UserState) session.getAttribute("userState");
        if (user == null) {
            // Not logged in.
            LOG.warn("Unauthenticated user access.");
            response.sendRedirect(String.format("%s/%s", request.getContextPath(), "browseCoursesLoadList"));
            return;
        }

        // Set navigation state
        NavigationState navState = new NavigationState("browseCourses");
        session.setAttribute("navState", navState);

        // Get page state
        BrowseCoursesPageState pageState = (BrowseCoursesPageState) session.getAttribute("browseCoursesPage");

        // Make sure course is selected
        if (pageState == null || !pageState.getHasSelectedCourse()) {
            response.sendRedirect(String.format("%s/%s", request.getContextPath(), "browseCoursesLoadList"));
            return;
        }

        try {
            // Add course to cart ...
            ServletContext context = getServletContext();
            RestClient client = (RestClient) context.getAttribute("restClient");
            client.cartAddCourseToCart(user.getUserId(), pageState.getSelectedCourse().getId());
        } catch (Exception e) {
            // ... or remove selection
            pageState.setSelectedCourse(null);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_URL);
        dispatcher.forward(request, response);
    }
}
