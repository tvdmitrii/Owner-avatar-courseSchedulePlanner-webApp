package com.turygin.servlet.catalog;

import com.turygin.api.client.RestClient;
import com.turygin.api.model.CourseDTO;
import com.turygin.api.model.DepartmentDTO;
import com.turygin.api.model.InstructorDTO;
import com.turygin.states.EditCatalogPageState;
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


/**
 * Main catalog servlet. Loads information about departments, instructors, courses, and associated sections
 * from REST API.
 */
@WebServlet(
        name = "CatalogLoadCourses",
        urlPatterns = { "/catalog" }
)
public class LoadCatalog extends HttpServlet {

    protected static final Logger LOG = LogManager.getLogger(LoadCatalog.class);

    /** Empty constructor. */
    public LoadCatalog() {}

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
        NavigationState navState = NavigationState.CATALOG;
        session.setAttribute("navState", navState);

        // Initialize page state
        EditCatalogPageState pageState = new EditCatalogPageState();

        // Fetch REST API client from the context
        ServletContext context = getServletContext();
        RestClient client = (RestClient) context.getAttribute("restClient");

        // Fetch department info from the API
        try (Response apiResponse = client.getAllDepartments()) {
            if(RestClient.isStatusSuccess(apiResponse)){
                List<DepartmentDTO> departments = apiResponse.readEntity(new GenericType<>() {});
                pageState.setDepartments(departments);
            } else {
                request.setAttribute("error", RestClient.getErrorMessage(apiResponse));
            }
        }

        // Fetch course info from the API
        try (Response apiResponse = client.getAllCourses()) {
            if(RestClient.isStatusSuccess(apiResponse)){
                List<CourseDTO> courses = apiResponse.readEntity(new GenericType<>() {});
                pageState.setCourses(courses);
            } else {
                request.setAttribute("error", RestClient.getErrorMessage(apiResponse));
            }
        }

        // Fetch instructor info from the API
        try (Response apiResponse = client.getAllInstructors()) {
            if(RestClient.isStatusSuccess(apiResponse)){
                List<InstructorDTO> instructors = apiResponse.readEntity(new GenericType<>() {});
                pageState.setInstructors(instructors);
            } else {
                request.setAttribute("error", RestClient.getErrorMessage(apiResponse));
            }
        }

        // Save page state in session
        session.setAttribute("editCatalogPage", pageState);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(navState.getJspPage());
        dispatcher.forward(request, response);
    }
}
