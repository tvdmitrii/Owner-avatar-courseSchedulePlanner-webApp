package com.turygin.servlet.catalog;

import com.turygin.api.client.RestClient;
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
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Servlet that selects a course or section that the user wants to edit and populates the editing area.
 */
@WebServlet(
        name = "CatalogSelectItem",
        urlPatterns = { "/catalog/select" }
)
public class SelectItem extends HttpServlet {

    /** Empty constructor. */
    public SelectItem() {}

    /**
     * Handles HTTP GET requests that contains either course list ID or section list ID.
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

        // Web filter ensures that page state was initialized.
        EditCatalogPageState pageState = (EditCatalogPageState) session.getAttribute("editCatalogPage");

        try {
            // Fetch REST API client from the context
            ServletContext context = getServletContext();
            RestClient client = (RestClient) context.getAttribute("restClient");

            String courseListIdString = request.getParameter("courseListId");
            if (courseListIdString != null) {
                // If selecting a course
                int courseListId = Integer.parseInt(courseListIdString);
                pageState.getCourses().setSelectedId(courseListId);

                // Send a request to load all course sections
                try (Response apiResponse = client.getAllCourseSections(pageState.getCourses().getSelected().getId())) {
                    if(RestClient.isStatusSuccess(apiResponse)) {
                        List<SectionDTO> sections = apiResponse.readEntity(new GenericType<>() {});
                        pageState.setSections(sections);
                        pageState.setIsCourseMode(true);
                    } else {
                        request.setAttribute("error", RestClient.getErrorMessage(apiResponse));
                    }
                }
            } else {
                // If selecting a section
                int sectionListId = Integer.parseInt(request.getParameter("sectionListId"));
                pageState.getSections().setSelectedId(sectionListId);
                pageState.setIsCourseMode(false);
            }

        } catch (Exception e) {
            // Something went wrong. Remove selection, remove sections, set to course mode.
            pageState.getCourses().resetSelected();
            pageState.setSections(null);
            pageState.setIsCourseMode(true);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(navState.getJspPage());
        dispatcher.forward(request, response);
    }
}
