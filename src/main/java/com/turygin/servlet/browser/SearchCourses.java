package com.turygin.servlet.browser;

import com.turygin.api.client.RestClient;
import com.turygin.api.model.CourseBasicDTO;
import com.turygin.states.BrowseCoursesPageState;
import com.turygin.states.nav.NavigationState;
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
 * Sample servlet that fetches information about all courses form the
 * REST API and sends it to JSP for display.
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
        NavigationState navState = NavigationState.BROWSER;
        session.setAttribute("navState", navState);

        // Get page state
        BrowseCoursesPageState pageState = (BrowseCoursesPageState) session.getAttribute("browseCoursesPage");

        // Set search title (if present)
        try {
            String searchTitle = request.getParameter("title");
            pageState.setTitleSearchTerm(searchTitle.trim().toLowerCase());
            LOG.debug("Found title search term '{}'.", pageState.getTitleSearchTerm());
        } catch (Exception e) {
            LOG.debug("Title search missing. Set to null.");
            pageState.setTitleSearchTerm(null);
        }

        // Set search department (if present)
        try {
            int departmentListId = Integer.parseInt(request.getParameter("departmentListId"));
            pageState.setSelectedDepartment(pageState.getLoadedDepartments().get(departmentListId));
            LOG.debug("Found search department with ID '{}'.", pageState.getSelectedDepartmentId());
        } catch (Exception e) {
            LOG.debug("Department search missing. Set to null.");
            pageState.setSelectedDepartment(null);
        }

        // Fetch REST API client from the context
        ServletContext context = getServletContext();
        RestClient client = (RestClient) context.getAttribute("restClient");

        // Search for courses using API
        List<CourseBasicDTO> courses = client.findCourses(pageState.getTitleSearchTerm(),
                pageState.getSelectedDepartmentId());
        pageState.setLoadedCourses(courses);
        LOG.debug("Found {} courses.", courses.size());

        // Reset selected course
        pageState.setSelectedCourse(null);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(navState.getJspPage());
        dispatcher.forward(request, response);
    }
}
