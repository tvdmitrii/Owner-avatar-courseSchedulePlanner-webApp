package com.turygin.servlet;

import com.turygin.api.client.RestClient;
import com.turygin.api.model.CourseBasicDTO;
import com.turygin.states.BrowseCoursesPageState;

import java.io.*;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


/**
 * Sample servlet that fetches information about all courses form the
 * REST API and sends it to JSP for display.
 */
@WebServlet(
        name = "BrowseCoursesLoadList",
        urlPatterns = { "", "/browseCoursesLoadList" }
)
public class BrowseCoursesLoadList extends HttpServlet {

    private static final String JSP_URL = "/browseCourses.jsp";

    /** Empty constructor. */
    public BrowseCoursesLoadList() {}

    /**
     * Handles HTTP GET requests.
     * @param request object that contains the client's request information
     * @param response object used to send the response back to the client
     * @throws ServletException if servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Fetch REST API client from the context
        ServletContext context = getServletContext();
        RestClient client = (RestClient) context.getAttribute("restClient");

        // Fetch course info from the API
        List<CourseBasicDTO> courses = client.getAllCourses();

        // Initialize page state and save it in session
        HttpSession session = request.getSession();
        BrowseCoursesPageState pageState = new BrowseCoursesPageState();
        pageState.setLoadedCourses(courses);
        session.setAttribute("browseCoursesPage", pageState);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_URL);
        dispatcher.forward(request, response);
    }
}
