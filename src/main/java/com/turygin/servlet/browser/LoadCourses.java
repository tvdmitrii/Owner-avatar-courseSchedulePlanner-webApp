package com.turygin.servlet.browser;

import com.turygin.api.client.RestClient;
import com.turygin.api.model.CourseBasicDTO;
import com.turygin.api.model.DepartmentBasicDTO;
import com.turygin.states.BrowseCoursesPageState;

import java.io.*;
import java.util.List;

import com.turygin.states.nav.NavigationState;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


/**
 * Sample servlet that fetches information about all courses form the
 * REST API and sends it to JSP for display.
 */
@WebServlet(
        name = "LoadCourses",
        urlPatterns = { "", "/browser/load" }
)
public class LoadCourses extends HttpServlet {

    /** Empty constructor. */
    public LoadCourses() {}

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

        // Initialize page state
        BrowseCoursesPageState pageState = new BrowseCoursesPageState();

        // Fetch REST API client from the context
        ServletContext context = getServletContext();
        RestClient client = (RestClient) context.getAttribute("restClient");

        // Fetch department info from the API
        List<DepartmentBasicDTO> departments = client.getAllDepartments();
        pageState.setLoadedDepartments(departments);

        // Fetch course info from the API
        List<CourseBasicDTO> courses = client.getAllCourses();
        pageState.setLoadedCourses(courses);

        // Save page state in session
        session.setAttribute("browseCoursesPage", pageState);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(navState.getJspPage());
        dispatcher.forward(request, response);
    }
}
