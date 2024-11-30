package com.turygin.servlet.catalog;

import com.turygin.api.client.RestClient;
import com.turygin.api.model.CourseDTO;
import com.turygin.states.BrowseCoursesPageState;
import com.turygin.states.EditCatalogPageState;
import com.turygin.states.nav.NavigationState;
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
 * Servlet for adding, updating, or removing a courses.
 */
@WebServlet(
        name = "CatalogEditCourse",
        urlPatterns = { "/catalog/course" }
)
public class ModifyCourse extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(ModifyCourse.class);

    /** Empty constructor. */
    public ModifyCourse() {}

    /**
     * Constructs course DTO object from form parameters.
     * @param pageState page state variable
     * @param request user request containing form parameters
     * @return course DTO
     */
    private CourseDTO processFormParameters(BrowseCoursesPageState pageState, HttpServletRequest request) {
        CourseDTO submittedCourse = new CourseDTO();
        submittedCourse.setTitle(request.getParameter("title"));
        submittedCourse.setDescription(request.getParameter("description"));
        submittedCourse.setCredits(Integer.parseInt(request.getParameter("credits")));
        submittedCourse.setNumber(Integer.parseInt(request.getParameter("number")));

        int departmentListId = Integer.parseInt(request.getParameter("departmentListId"));
        submittedCourse.setDepartment(pageState.getDepartments().getItems().get(departmentListId));

        return submittedCourse;
    }

    /**
     * Handles HTTP POST requests containing course information.
     * @param request object that contains the client's request information
     * @param response object used to send the response back to the client
     * @throws ServletException if servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Set navigation state
        NavigationState navState = NavigationState.CATALOG;
        session.setAttribute("navState", navState);

        // Web filter ensures that page state was initialized.
        EditCatalogPageState pageState = (EditCatalogPageState) session.getAttribute("editCatalogPage");

        try {
            // "action" parameter can be either "add", "delete" or "update"
            String action = request.getParameter("action");
            assert action != null;

            // Fetch REST API client from the context
            ServletContext context = getServletContext();
            RestClient client = (RestClient) context.getAttribute("restClient");

            // Add course
            if (action.equals("add")) {
                LOG.debug("Adding course.");
                CourseDTO submittedCourse = processFormParameters(pageState, request);

                // Send add course request
                try (Response apiResponse = client.addCourse(submittedCourse)) {
                    if (RestClient.isStatusSuccess(apiResponse)) {
                        CourseDTO newCourse = apiResponse.readEntity(CourseDTO.class);
                        pageState.getCourses().getItems().add(newCourse);
                        request.setAttribute("success", String.format("%s course has been added.",
                                newCourse.getCode()));
                        LOG.debug("Added course: {}", newCourse.toString());
                    } else {
                        String error = RestClient.getErrorMessage(apiResponse);
                        LOG.error(error);
                        request.setAttribute("error", error);
                    }
                }

                forwardToJsp(request, response, navState);
                return;
            }

            // If not adding a course, ensure there is a selected course
            if (!pageState.getCourses().getHasSelected()) {
                LOG.warn("Course is not selected.");
                response.sendRedirect(String.format("%s/%s", request.getContextPath(), navState.getDefaultServlet()));
                return;
            }

            // Delete course
            if (action.equals("delete")) {
                // Send delete course request
                try (Response apiResponse = client.deleteCourse(pageState.getCourses().getSelected().getId())) {
                    if (RestClient.isStatusSuccess(apiResponse)) {
                        String courseCode = pageState.getCourses().getSelected().getCode();
                        pageState.getCourses().removeSelected();
                        request.setAttribute("success", String.format("%s course has been removed.",
                                courseCode));
                        LOG.debug("Removed course: {}", courseCode);
                    } else {
                        String error = RestClient.getErrorMessage(apiResponse);
                        LOG.error(error);
                        request.setAttribute("error", error);
                    }
                }
            } else if (action.equals("update")) {
                // Construct course DTO
                CourseDTO submittedCourse = processFormParameters(pageState, request);
                submittedCourse.setId(pageState.getCourses().getSelected().getId());

                // Send course update request
                try (Response apiResponse = client.updateCourse(submittedCourse)) {
                    if (RestClient.isStatusSuccess(apiResponse)) {
                        CourseDTO updatedCourse = apiResponse.readEntity(CourseDTO.class);
                        pageState.getCourses().updateSelected(updatedCourse);
                        request.setAttribute("success", String.format("%s course has been updated.",
                                updatedCourse.getCode()));
                        LOG.debug("Updated course: {}", updatedCourse.toString());
                    } else {
                        String error = RestClient.getErrorMessage(apiResponse);
                        request.setAttribute("error", error);
                        LOG.error(error);
                    }
                }
            } else {
                LOG.warn("Unknown action: {}.", action);
            }
        } catch (Exception e) {
            // ... or ignore
            LOG.warn("Could not modify course via REST API. Exception: {}", e.getMessage());
        }

        forwardToJsp(request, response, navState);
    }

    /**
     * Helper method that forwards to a JSP.
     * @param request client request
     * @param response response object
     * @param navState navigation state object
     * @throws ServletException if servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    private void forwardToJsp(HttpServletRequest request, HttpServletResponse response, NavigationState navState)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(navState.getJspPage());
        dispatcher.forward(request, response);
    }
}
