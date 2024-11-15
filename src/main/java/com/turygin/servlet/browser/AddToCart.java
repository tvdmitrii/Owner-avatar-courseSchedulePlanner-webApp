package com.turygin.servlet.browser;

import com.turygin.api.client.RestClient;
import com.turygin.api.model.CourseDTO;
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
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


/**
 * Adds selected course to users cart.
 */
@WebServlet(
        name = "AddToCart",
        urlPatterns = { "/browser/addToCart" }
)
public class AddToCart extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(AddToCart.class);

    /** Empty constructor. */
    public AddToCart() {}

    /**
     * Handles HTTP POST requests.
     * @param request object that contains the client's request information
     * @param response object used to send the response back to the client
     * @throws ServletException if servlet error occurs
     * @throws IOException if an I/O error occurs
     */
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
                    NavigationState.BROWSER.getDefaultServlet()));
            return;
        }

        try {
            // Add course to cart ...
            ServletContext context = getServletContext();
            RestClient client = (RestClient) context.getAttribute("restClient");
            Response cartAddCourseResponse =
                    client.cartAddCourse(user.getUserId(), pageState.getCourses().getSelected().getId());
            if(RestClient.isStatusSuccess(cartAddCourseResponse)) {
                CourseDTO addedCourse = cartAddCourseResponse.readEntity(CourseDTO.class);
                request.setAttribute("success", String.format("%s has been added to cart.", addedCourse.getCode()));
            } else {
                request.setAttribute("error", RestClient.getErrorMessage(cartAddCourseResponse));
            }
        } catch (Exception e) {
            // ... or remove selection
            pageState.getCourses().resetSelected();
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(navState.getJspPage());
        dispatcher.forward(request, response);
    }
}
