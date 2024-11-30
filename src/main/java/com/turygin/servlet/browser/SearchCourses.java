package com.turygin.servlet.browser;

import com.turygin.api.client.RestClient;
import com.turygin.api.model.CourseDTO;
import com.turygin.states.BrowseCoursesPageState;
import com.turygin.states.nav.NavigationState;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet that performs a course search via REST API and updates the course list of course browser.
 */
@WebServlet(
        name = "SearchCourses",
        urlPatterns = { "/browser/search" }
)
public class SearchCourses extends HttpServlet {

    protected static final Logger LOG = LogManager.getLogger(SearchCourses.class);

    /** Empty constructor. */
    public SearchCourses() {}

    /**
     * Handles HTTP GET requests which contain search query parameters.
     * @param request object that contains the client's request information
     * @param response object used to send the response back to the client
     * @throws ServletException if servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Set navigation state
        NavigationState navState = NavigationState.BROWSER;
        session.setAttribute("navState", navState);

        // Web filter ensures that page state was initialized.
        BrowseCoursesPageState pageState = (BrowseCoursesPageState) session.getAttribute("browseCoursesPage");

        // Set search title (if present).
        // "title" query parameter contains course title substring to search for.
        try {
            String searchTitle = request.getParameter("title");
            pageState.setTitleSearchTerm(searchTitle.trim().toLowerCase());
            LOG.debug("Found title search term '{}'.", pageState.getTitleSearchTerm());
        } catch (Exception e) {
            LOG.debug("Title search missing. Set to null.");
            pageState.setTitleSearchTerm(null);
        }

        // Set search department (if present).
        // "departmentListId" query parameter contains ID of the department in internal department list.
        try {
            int departmentListId = Integer.parseInt(request.getParameter("departmentListId"));
            pageState.getDepartments().setSelectedId(departmentListId);
            LOG.debug("Found search department with ID '{}'.", departmentListId);
        } catch (Exception e) {
            LOG.debug("Department ID is missing. Reset selection.");
            pageState.getDepartments().resetSelected();
        }

        // Fetch REST API client from the context
        ServletContext context = getServletContext();
        RestClient client = (RestClient) context.getAttribute("restClient");

        // Set department database ID to ID of the selected department, or to -1 if none selected.
        long departmentId = pageState.getDepartments().getHasSelected()
                ? pageState.getDepartments().getSelected().getId() : -1;

        // Send search request
        try (Response apiResponse = client.findCourses(pageState.getTitleSearchTerm(), departmentId)) {
            if (RestClient.isStatusSuccess(apiResponse)) {
                List<CourseDTO> courses = apiResponse.readEntity(new GenericType<>() {});
                pageState.setCourses(courses);
                LOG.debug("Found {} courses.", courses.size());
            } else {
                request.setAttribute("error", RestClient.getErrorMessage(apiResponse));
            }
        }

        // Reset selected course
        pageState.getCourses().resetSelected();

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(navState.getJspPage());
        dispatcher.forward(request, response);
    }
}
