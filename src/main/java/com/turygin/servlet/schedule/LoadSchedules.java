package com.turygin.servlet.schedule;

import com.turygin.api.client.RestClient;
import com.turygin.api.model.ScheduleDTO;
import com.turygin.states.SchedulePageState;
import com.turygin.states.UserState;
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
 * Loads information about departments, instructors, courses, and associated sections from REST API.
 */
@WebServlet(
        name = "LoadSchedules",
        urlPatterns = { "/schedule" }
)
public class LoadSchedules extends HttpServlet {

    protected static final Logger LOG = LogManager.getLogger(LoadSchedules.class);

    /** Empty constructor. */
    public LoadSchedules() {}

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
        NavigationState navState = NavigationState.SCHEDULE;
        session.setAttribute("navState", navState);
        //UserState user = (UserState) session.getAttribute("userState");

        // Initialize page state
        SchedulePageState pageState = new SchedulePageState();

        // Fetch REST API client from the context
        ServletContext context = getServletContext();
        RestClient client = (RestClient) context.getAttribute("restClient");

        // Generate and fetch schedules from the API
        Response scheduleResponse = client.getSchedules(1);//client.getSchedules(user.getUserId());
        if(RestClient.isStatusSuccess(scheduleResponse)){
            List<ScheduleDTO> schedules = scheduleResponse.readEntity(new GenericType<>() {});
            pageState.setSchedules(schedules);
            if (!schedules.isEmpty()) {
                pageState.getSchedules().setSelectedId(0);
            }
        } else {
            request.setAttribute("error", RestClient.getErrorMessage(scheduleResponse));
            forwardToJsp(request, response, navState);
            return;
        }

        // Save page state in session
        session.setAttribute("schedulePage", pageState);

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
