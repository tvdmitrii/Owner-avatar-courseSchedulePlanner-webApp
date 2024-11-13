package com.turygin.servlet.admin;

import com.turygin.api.client.RestClient;
import com.turygin.api.model.CourseBasicDTO;
import com.turygin.api.model.CourseWithSectionsDTO;
import com.turygin.states.BrowseCoursesPageState;
import com.turygin.states.UserState;
import com.turygin.states.ViewCartPageState;
import com.turygin.states.nav.NavigationState;
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
import java.util.List;
import java.util.stream.Stream;


@WebServlet(
        name = "AdminEditCourse",
        urlPatterns = { "/admin/course/edit" }
)
public class EditCourse extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(EditCourse.class);

    /** Empty constructor. */
    public EditCourse() {}

    private CourseBasicDTO processFormParameters(BrowseCoursesPageState pageState, HttpServletRequest request) {
        int departmentListId = Integer.parseInt(request.getParameter("departmentListId"));
        CourseBasicDTO submittedCourse = new CourseBasicDTO();
        submittedCourse.setTitle(request.getParameter("title"));
        submittedCourse.setDescription(request.getParameter("description"));
        submittedCourse.setCredits(Integer.parseInt(request.getParameter("credits")));
        submittedCourse.setNumber(Integer.parseInt(request.getParameter("number")));
        submittedCourse.setDepartmentId(pageState.getDepartments().getItems().get(departmentListId).getId());
        return submittedCourse;
    }

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
                CourseBasicDTO submittedCourse = processFormParameters(pageState, request);
                client.addCourse(submittedCourse);
                response.sendRedirect(String.format("%s/%s", request.getContextPath(), navState.getDefaultServlet()));
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
                LOG.debug("Removing course.");
                client.deleteCourse(pageState.getCourses().getSelected().getId());
                pageState.getCourses().removeSelected();
            } else if (action.equals("update")) {
                // Update course
                LOG.debug("Updating course.");
                CourseBasicDTO submittedCourse = processFormParameters(pageState, request);
                submittedCourse.setId(pageState.getCourses().getSelected().getId());
                CourseBasicDTO updatedCourse = client.updateCourse(submittedCourse);
                pageState.getCourses().updateSelected(updatedCourse);
            } else {
                LOG.warn("Unknown action: {}.", action);
            }
        } catch (Exception e) {
            // ... or ignore
            LOG.warn("Could not modify course via REST API. Exception: {}", e.getMessage());
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(navState.getJspPage());
        dispatcher.forward(request, response);
    }
}
