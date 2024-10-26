package com.turygin.servlet;

import com.turygin.api.client.RestClient;
import com.turygin.api.model.CourseBasicDTO;
import com.turygin.states.BrowseCoursesPageState;

import jakarta.servlet.RequestDispatcher;
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
        name = "BrowseCoursesSelectCourse",
        urlPatterns = { "/browseCoursesSelectCourse" }
)
public class BrowseCoursesSelectCourse extends HttpServlet {

    private static final String JSP_URL = "/browseCourses.jsp";
    private static final String BROWSE_COURSES_LOAD_SERVLET = "browseCoursesLoadList";

    /** Empty constructor. */
    public BrowseCoursesSelectCourse() {}

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
            response.sendRedirect(String.format("%s/%s", request.getContextPath(), BROWSE_COURSES_LOAD_SERVLET));
            return;
        }

        try {
            // Select a course from the list if the index is valid ...
            int courseListId = Integer.parseInt(request.getParameter("courseListId"));
            pageState.setSelectedCourse(pageState.getLoadedCourses().get(courseListId));
        } catch (Exception e) {
            // ... or remove selection
            pageState.setSelectedCourse(null);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_URL);
        dispatcher.forward(request, response);
    }
}
