package com.turygin.servlet.catalog;

import com.turygin.api.client.RestClient;
import com.turygin.api.model.DaysOfWeekDTO;
import com.turygin.api.model.MeetingTimeDTO;
import com.turygin.api.model.SectionDTO;
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
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


/**
 * Servlet for adding, updating, or removing a section.
 */
@WebServlet(
        name = "CatalogEditSection",
        urlPatterns = { "/catalog/section" }
)
public class ModifySection extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(ModifySection.class);

    /** Empty constructor. */
    public ModifySection() {}

    /**
     * Constructs section DTO object from form parameters.
     * @param pageState page state variable
     * @param request user request containing form parameters
     * @return section DTO
     */
    private SectionDTO processFormParameters(EditCatalogPageState pageState, HttpServletRequest request) {
        SectionDTO submittedSection = new SectionDTO();

        // Parse days of week
        DaysOfWeekDTO daysOfWeek = new DaysOfWeekDTO();
        Boolean[] selectedDays = new Boolean[]{false, false, false, false, false};
        String[] weekDaysStrings = request.getParameterValues("weekDays");
        assert weekDaysStrings != null;
        assert weekDaysStrings.length > 0;
        for (String weekDay : weekDaysStrings) {
            selectedDays[Integer.parseInt(weekDay)] = true;
        }
        daysOfWeek.setDaysOfWeek(selectedDays);
        submittedSection.setDaysOfWeek(daysOfWeek);

        // Parse start time
        MeetingTimeDTO startTime = new MeetingTimeDTO();
        startTime.setHours(Integer.parseInt(request.getParameter("startTimeHours")));
        startTime.setMinutes(Integer.parseInt(request.getParameter("startTimeMinutes")));
        startTime.setPastNoon(Boolean.parseBoolean(request.getParameter("startTimeMeridian")));
        submittedSection.setStartTime(startTime);

        // Parse end time
        MeetingTimeDTO endTime = new MeetingTimeDTO();
        endTime.setHours(Integer.parseInt(request.getParameter("endTimeHours")));
        endTime.setMinutes(Integer.parseInt(request.getParameter("endTimeMinutes")));
        endTime.setPastNoon(Boolean.parseBoolean(request.getParameter("endTimeMeridian")));
        submittedSection.setEndTime(endTime);

        // Select instructor
        int instructorListId = Integer.parseInt(request.getParameter("instructorListId"));
        submittedSection.setInstructor(pageState.getInstructors().getItems().get(instructorListId));

        return submittedSection;
    }

    /**
     * Handles HTTP POST requests which contain section information.
     * @param request object that contains the client's request information
     * @param response object used to send the response back to the client
     * @throws ServletException if servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Set navigation state
        NavigationState navState = NavigationState.CATALOG;
        session.setAttribute("navState", navState);

        // Web filter ensures that page state was initialized.
        EditCatalogPageState pageState = (EditCatalogPageState) session.getAttribute("editCatalogPage");

        try {
            // "action" parameter can be either "add", "delete" or "update"
            String action = request.getParameter("action");
            assert action != null;

            // Fetch REST API client from the context
            ServletContext context = getServletContext();
            RestClient client = (RestClient) context.getAttribute("restClient");

            // Add section
            if (action.equals("add")) {
                LOG.debug("Adding section.");
                SectionDTO submittedSection = processFormParameters(pageState, request);

                // Send add section request
                try (Response apiResponse = client.addSection(pageState.getCourses().getSelected().getId(),
                        submittedSection)) {
                    if (RestClient.isStatusSuccess(apiResponse)) {
                        SectionDTO newSection = apiResponse.readEntity(SectionDTO.class);
                        pageState.getSections().getItems().add(newSection);
                        request.setAttribute("success","Section has been added.");
                        LOG.debug("Added section: {}", newSection.toString());
                    } else {
                        String error = RestClient.getErrorMessage(apiResponse);
                        LOG.error(error);
                        request.setAttribute("error", error);
                    }
                }

                forwardToJsp(request, response, navState);
                return;
            }

            // If not adding a section, ensure a section is selected
            if (!pageState.getSections().getHasSelected()) {
                LOG.warn("Section is not selected.");
                response.sendRedirect(String.format("%s/%s", request.getContextPath(), navState.getDefaultServlet()));
                return;
            }

            // Delete section
            if (action.equals("delete")) {
                // Send delete section request
                try (Response apiResponse = client.deleteSection(pageState.getSections().getSelected().getId())) {
                    if (RestClient.isStatusSuccess(apiResponse)) {
                        pageState.getSections().removeSelected();
                        request.setAttribute("success", "Section has been removed.");
                        LOG.debug("Removed section.");
                    } else {
                        String error = RestClient.getErrorMessage(apiResponse);
                        LOG.error(error);
                        request.setAttribute("error", error);
                    }
                }
            } else if (action.equals("update")) {
                // Update section
                SectionDTO submittedSection = processFormParameters(pageState, request);
                submittedSection.setId(pageState.getSections().getSelected().getId());

                // Send update section request
                try (Response apiResponse = client.updateSection(submittedSection)) {
                    if (RestClient.isStatusSuccess(apiResponse)) {
                        SectionDTO updatedSection = apiResponse.readEntity(SectionDTO.class);
                        pageState.getSections().updateSelected(updatedSection);
                        request.setAttribute("success", "Section has been updated.");
                        LOG.debug("Updated section: {}", updatedSection.toString());
                    } else {
                        String error = RestClient.getErrorMessage(apiResponse);
                        request.setAttribute("error", error);
                        LOG.error(error);
                    }
                }
            } else {
                LOG.warn("Unknown action: {}.", action);
            }
        } catch (Exception e) {
            // ... or ignore
            LOG.warn("Could not modify section via REST API. Exception: {}", e.getMessage());
        }

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
