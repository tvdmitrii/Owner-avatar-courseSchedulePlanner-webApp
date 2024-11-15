package com.turygin.servlet.admin;

import com.turygin.api.client.RestClient;
import com.turygin.api.model.CourseDTO;
import com.turygin.states.BrowseCoursesPageState;
import com.turygin.states.UserState;
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
 * Administrator servlet for adding, updating, or removing a course.
 */
@WebServlet(
        name = "AdminEditCourse",
        urlPatterns = { "/admin/course/edit" }
)
public class EditCourse extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(EditCourse.class);

    /** Empty constructor. */
    public EditCourse() {}

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

        // Get page state
        BrowseCoursesPageState pageState = (BrowseCoursesPageState) session.getAttribute("browseCoursesPage");

        try {
            String action = request.getParameter("action");
            assert action != null;

            // Fetch REST API client from the context
            ServletContext context = getServletContext();
            RestClient client = (RestClient) context.getAttribute("restClient");
            UserState user = (UserState) session.getAttribute("userState");

            // Add course and reload course list
            if (action.equals("add")) {
                LOG.debug("Adding course.");
                CourseDTO submittedCourse = processFormParameters(pageState, request);

                Response addCourseResponse = client.addCourse(submittedCourse);
                if (RestClient.isStatusSuccess(addCourseResponse)) {
                    CourseDTO newCourse = addCourseResponse.readEntity(CourseDTO.class);
                    pageState.getCourses().getItems().add(newCourse);
                    request.setAttribute("success", String.format("%s course has been added.",
                            newCourse.getCode()));
                    LOG.debug("Added course: {}", newCourse.toString());
                } else {
                    String error = RestClient.getErrorMessage(addCourseResponse);
                    LOG.error(error);
                    request.setAttribute("error", error);
                }

                forwardToJsp(request, response, navState);
                return;
            }

            // If not adding a course, ensure it is selected
            if (!pageState.getCourses().getHasSelected()) {
                LOG.warn("Course is not selected.");
                response.sendRedirect(String.format("%s/%s", request.getContextPath(), navState.getDefaultServlet()));
                return;
            }

            // Delete course
            if (action.equals("delete")) {
                Response removeResponse = client.deleteCourse(pageState.getCourses().getSelected().getId());
                if (RestClient.isStatusSuccess(removeResponse)) {
                    String courseCode = pageState.getCourses().getSelected().getCode();
                    pageState.getCourses().removeSelected();
                    request.setAttribute("success", String.format("%s course has been removed.",
                            courseCode));
                    LOG.debug("Removed course: {}", courseCode);
                } else {
                    String error = RestClient.getErrorMessage(removeResponse);
                    LOG.error(error);
                    request.setAttribute("error", error);
                }
            } else if (action.equals("update")) {
                // Update course
                CourseDTO submittedCourse = processFormParameters(pageState, request);
                submittedCourse.setId(pageState.getCourses().getSelected().getId());

                Response updateResponse = client.updateCourse(submittedCourse);
                if (RestClient.isStatusSuccess(updateResponse)) {
                    CourseDTO updatedCourse = updateResponse.readEntity(CourseDTO.class);
                    pageState.getCourses().updateSelected(updatedCourse);
                    request.setAttribute("success", String.format("%s course has been updated.",
                            updatedCourse.getCode()));
                    LOG.debug("Updated course: {}", updatedCourse.toString());
                } else {
                    String error = RestClient.getErrorMessage(updateResponse);
                    request.setAttribute("error", error);
                    LOG.error(error);
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
