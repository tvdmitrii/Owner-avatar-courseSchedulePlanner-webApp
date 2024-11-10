package com.turygin.servlet.browser;

import com.turygin.api.client.RestClient;
import com.turygin.states.BrowseCoursesPageState;
import com.turygin.states.nav.NavigationState;
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
        name = "AddToCart",
        urlPatterns = { "/browser/addToCart" }
)
public class AddToCart extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(AddToCart.class);

    /** Empty constructor. */
    public AddToCart() {}

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Set navigation state
        NavigationState navState = NavigationState.BROWSER;
        session.setAttribute("navState", navState);

        // Check that logged in.
        UserState user = (UserState) session.getAttribute("userState");
        if (user == null) {
            // Not logged in.
            LOG.warn("Unauthenticated user access.");
            response.sendRedirect(String.format("%s/%s", request.getContextPath(), NavigationState.HOME));
            return;
        }

        // Get page state
        BrowseCoursesPageState pageState = (BrowseCoursesPageState) session.getAttribute("browseCoursesPage");

        // Make sure course is selected
        if (!pageState.getCourses().getHasSelected()) {
            response.sendRedirect(String.format("%s/%s", request.getContextPath(),
                    NavigationState.CART.getDefaultServlet()));
            return;
        }

        try {
            // Add course to cart ...
            ServletContext context = getServletContext();
            RestClient client = (RestClient) context.getAttribute("restClient");
            client.cartAddCourseToCart(user.getUserId(), pageState.getCourses().getSelected().getId());
        } catch (Exception e) {
            // ... or remove selection
            pageState.getCourses().resetSelected();
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(navState.getJspPage());
        dispatcher.forward(request, response);
    }
}
