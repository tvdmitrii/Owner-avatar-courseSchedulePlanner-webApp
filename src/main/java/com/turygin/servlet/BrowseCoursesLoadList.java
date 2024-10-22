package com.turygin.servlet;

import com.turygin.api.client.RestClient;
import com.turygin.api.model.CourseBasicDTO;

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
        urlPatterns = { "/browseCoursesLoadList" }
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


        RestClient client = new RestClient();
        List<CourseBasicDTO> courses = client.getAllCourses();

        HttpSession session = request.getSession();
        session.setAttribute("browseCourseList", courses);
        session.setAttribute("browseSelectedCourse", null);
        session.setAttribute("browseSelectedCourseIndex", null);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_URL);
        dispatcher.forward(request, response);
    }
}
