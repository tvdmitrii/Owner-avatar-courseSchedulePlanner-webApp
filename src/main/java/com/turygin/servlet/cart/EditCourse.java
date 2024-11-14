package com.turygin.servlet.cart;

import com.turygin.api.client.RestClient;
import com.turygin.api.model.CourseWithSectionsDTO;
import com.turygin.states.nav.NavigationState;
import com.turygin.states.UserState;
import com.turygin.states.ViewCartPageState;
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
import java.util.List;
import java.util.stream.Stream;

/**
 * Handles removing a course from the cart or updating its selected sections.
 */
@WebServlet(
        name = "CartEditCourse",
        urlPatterns = { "/cart/edit" }
)
public class EditCourse extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(EditCourse.class);

    /** Empty constructor. */
    public EditCourse() {}

    /**
     * Handles the update course sections request.
     * @param request user request
     * @param user user state variable
     * @param pageState page state variable
     * @param client REST API client
     */
    private void updateCourse(HttpServletRequest request, UserState user, ViewCartPageState pageState,
                              RestClient client) {
        // Convert comma-delimited string to list of longs ...
        String[] idStrings = request.getParameterValues("sectionIds");
        // If user unselects all sections, empty array will not be transmitted
        if (idStrings == null) {
            idStrings = new String[0];
        }
        LOG.debug("Raw request section ID data: {}", String.join(",", idStrings));
        List<Long> sectionIds = Stream.of(idStrings).map(Long::parseLong).toList();
        LOG.debug("Parsed section IDs: {}", sectionIds);

        // Update course selection on the server
        Response courseResponse = client.
                cartUpdateSections(user.getUserId(), pageState.getCourses().getSelected().getId(), sectionIds);
        if(RestClient.isStatusSuccess(courseResponse)) {
            CourseWithSectionsDTO updatedCourse = RestClient.getDTO(courseResponse, CourseWithSectionsDTO.class);
            pageState.getCourses().updateSelected(updatedCourse);
            request.setAttribute("success", String.format("Section selection for %s has been updated.",
                    updatedCourse.getCode()));
            LOG.debug("Course updated: {}", updatedCourse.toString());
        } else {
            String error = RestClient.getErrorMessage(courseResponse);
            LOG.error(error);
            request.setAttribute("error", error);
        }
    }

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
        NavigationState navState = NavigationState.CART;
        session.setAttribute("navState", navState);

        // Get page state
        ViewCartPageState pageState = (ViewCartPageState) session.getAttribute("viewCartPage");

        // Check that view cart page was initialized and a course is selected
        if (!pageState.getCourses().getHasSelected()) {
            LOG.warn("Cart courses not loaded.");
            response.sendRedirect(String.format("%s/%s", request.getContextPath(), navState.getDefaultServlet()));
            return;
        }

        try {
            String action = request.getParameter("action");
            assert action != null;

            // Fetch REST API client from the context
            ServletContext context = getServletContext();
            RestClient client = (RestClient) context.getAttribute("restClient");
            UserState user = (UserState) session.getAttribute("userState");

            if (action.equals("save")) {
                LOG.debug("Updating course.");
                updateCourse(request, user, pageState, client);
            } else if (action.equals("delete")) {
                Response removeResponse = client.cartRemoveCourse(user.getUserId(), pageState.getCourses().getSelected().getId());
                if(RestClient.isStatusSuccess(removeResponse)) {
                    pageState.getCourses().removeSelected();
                    LOG.debug("Removed course.");
                    removeResponse.close();
                } else {
                    String error = RestClient.getErrorMessage(removeResponse);
                    LOG.error(error);
                    request.setAttribute("error", error);
                }
            } else {
                LOG.warn("Unknown action: {}.", action);
            }
        } catch (Exception e) {
            // ... or ignore
            LOG.warn("Could not update course via REST API. Exception: {}", e.getMessage());
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(navState.getJspPage());
        dispatcher.forward(request, response);
    }
}
