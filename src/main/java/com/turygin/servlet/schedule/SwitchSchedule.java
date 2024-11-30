package com.turygin.servlet.schedule;


import com.turygin.states.SchedulePageState;
import com.turygin.states.nav.NavigationState;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


/**
 * Servlet that moves forward or backward through available schedules.
 */
@WebServlet(
        name = "SwitchSchedule",
        urlPatterns = { "/schedule/switch" }
)
public class SwitchSchedule extends HttpServlet {

    protected static final Logger LOG = LogManager.getLogger(SwitchSchedule.class);

    /** Empty constructor. */
    public SwitchSchedule() {}

    /**
     * Handles HTTP POST requests containing either next or previous actions.
     * @param request object that contains the client's request information
     * @param response object used to send the response back to the client
     * @throws ServletException if servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Set navigation state
        NavigationState navState = NavigationState.SCHEDULE;
        session.setAttribute("navState", navState);

        // Web filter ensures that page was initialized.
        SchedulePageState pageState = (SchedulePageState) session.getAttribute("schedulePage");

        // "action" parameter can be either "next" or "previous"
        String action = request.getParameter("action");
        assert action != null;

        // Safely increment or decrement selected schedule ID
        int scheduleListId = pageState.getSchedules().getSelectedId();
        if (action.equals("next")) {
            scheduleListId = Math.min(scheduleListId + 1, pageState.getSchedules().getSize() - 1);
        } else if (action.equals("previous")) {
            scheduleListId = Math.max(scheduleListId - 1, 0);
        } else {
            LOG.warn("Unknown action: {}.", action);
        }

        // Select new schedule
        pageState.getSchedules().setSelectedId(scheduleListId);

        // Save page state in session
        session.setAttribute("schedulePage", pageState);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(navState.getJspPage());
        dispatcher.forward(request, response);
    }
}
