package com.turygin.servlet;

import com.turygin.api.client.RestClient;
import com.turygin.api.model.CourseDTO;

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
        name = "CourseServlet",
        urlPatterns = { "/courseServlet" }
)
public class CourseServlet extends HttpServlet {

    /** Empty constructor. */
    public CourseServlet() {}

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
        List<CourseDTO> courses = client.getAllCourses();
        request.setAttribute("courses", courses);

        String url = "/index.jsp";

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
