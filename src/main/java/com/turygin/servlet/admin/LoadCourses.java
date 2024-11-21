package com.turygin.servlet.admin;

import com.turygin.api.client.RestClient;
import com.turygin.api.model.CourseDTO;
import com.turygin.api.model.DepartmentDTO;
import com.turygin.api.model.InstructorDTO;
import com.turygin.states.EditCoursesPageState;
import com.turygin.states.nav.NavigationState;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;


@WebServlet(
        name = "AdminLoadCourses",
        urlPatterns = { "/admin/load" }
)
public class LoadCourses extends HttpServlet {

    protected static final Logger LOG = LogManager.getLogger(LoadCourses.class);

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
        NavigationState navState = NavigationState.ADMIN;
        session.setAttribute("navState", navState);

        // Initialize page state
        EditCoursesPageState pageState = new EditCoursesPageState();

        // Fetch REST API client from the context
        ServletContext context = getServletContext();
        RestClient client = (RestClient) context.getAttribute("restClient");

        // Fetch department info from the API
        Response departmentsResponse = client.getAllDepartments();
        if(RestClient.isStatusSuccess(departmentsResponse)){
            List<DepartmentDTO> departments = departmentsResponse.readEntity(new GenericType<>() {});
            pageState.setDepartments(departments);
        } else {
            request.setAttribute("error", RestClient.getErrorMessage(departmentsResponse));
            forwardToJsp(request, response, navState);
            return;
        }

        // Fetch course info from the API
        Response coursesResponse = client.getAllCourses();
        if(RestClient.isStatusSuccess(coursesResponse)){
            List<CourseDTO> courses = coursesResponse.readEntity(new GenericType<>() {});
            pageState.setCourses(courses);
        } else {
            request.setAttribute("error", RestClient.getErrorMessage(coursesResponse));
            forwardToJsp(request, response, navState);
            return;
        }

        // Fetch instructor info from the API
        Response instructorsResponse = client.getAllInstructors();
        if(RestClient.isStatusSuccess(instructorsResponse)){
            List<InstructorDTO> instructors = instructorsResponse.readEntity(new GenericType<>() {});
            pageState.setInstructors(instructors);
        } else {
            request.setAttribute("error", RestClient.getErrorMessage(instructorsResponse));
            forwardToJsp(request, response, navState);
            return;
        }

        // Save page state in session
        session.setAttribute("editCoursesPage", pageState);

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
