package com.turygin.servlet;

import com.turygin.api.client.RestClient;
import com.turygin.api.model.CourseBasicDTO;
import com.turygin.api.model.DepartmentBasicDTO;
import com.turygin.states.BrowseCoursesPageState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Sample servlet that fetches information about all courses form the
 * REST API and sends it to JSP for display.
 */
@WebServlet(
        name = "BrowseCoursesSearch",
        urlPatterns = { "/browseCoursesSearch" }
)
public class BrowseCoursesSearch extends HttpServlet {

    protected static final Logger LOG = LogManager.getLogger(BrowseCoursesSearch.class);
    private static final String JSP_URL = "/browseCourses.jsp";
    private static final String BROWSE_COURSES_LOAD_SERVLET = "browseCoursesLoadList";

    /** Empty constructor. */
    public BrowseCoursesSearch() {}

    /**
     * Handles HTTP GET requests.
     * @param request object that contains the client's request information
     * @param response object used to send the response back to the client
     * @throws ServletException if servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get page state
        HttpSession session = request.getSession();
        BrowseCoursesPageState pageState = (BrowseCoursesPageState) session.getAttribute("browseCoursesPage");

        if (pageState == null) {
            // Should not be here yet. Course list was not initialized.
            LOG.debug("Page state is null. Leaving search...");
            response.sendRedirect(String.format("%s/%s", request.getContextPath(), BROWSE_COURSES_LOAD_SERVLET));
            return;
        }

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

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_URL);
        dispatcher.forward(request, response);
    }
}
