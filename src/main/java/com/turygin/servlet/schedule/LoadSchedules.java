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
 * Servlet that requests available schedules from REST API based on user's cart.
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

        // Web filter ensures that user has been logged in.
        UserState user = (UserState) session.getAttribute("userState");

        // Initialize page state
        SchedulePageState pageState = new SchedulePageState();

        // Fetch REST API client from the context
        ServletContext context = getServletContext();
        RestClient client = (RestClient) context.getAttribute("restClient");

        // Send a request to generate and fetch schedules from the API
        try (Response apiResponse = client.getSchedules(user.getUserId())) {
            if(RestClient.isStatusSuccess(apiResponse)){
                List<ScheduleDTO> schedules = apiResponse.readEntity(new GenericType<>() {});
                pageState.setSchedules(schedules);
                if (!schedules.isEmpty()) {
                    pageState.getSchedules().setSelectedId(0);
                }
            } else {
                request.setAttribute("error", RestClient.getErrorMessage(apiResponse));
            }
        }

        // Save page state in session
        session.setAttribute("schedulePage", pageState);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(navState.getJspPage());
        dispatcher.forward(request, response);
    }
}
