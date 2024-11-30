package com.turygin.servlet.catalog;

import com.turygin.states.EditCatalogPageState;
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
 * Servlet for creating a blank course or section for user to edit.
 */
@WebServlet(
        name = "CatalogNewItem",
        urlPatterns = { "/catalog/new" }
)
public class NewItem extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(NewItem.class);

    /** Empty constructor. */
    public NewItem() {}

    /**
     * Handles HTTP POST requests.
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
            // "action" parameter can be either "course" or "section"
            String action = request.getParameter("action");
            assert action != null;

            // Setup variables for either course or section editing
            if (action.equals("course")) {
                pageState.setIsCourseMode(true);
                pageState.getSections().resetSelected();
                pageState.getCourses().resetSelected();
            } else if (action.equals("section") && pageState.getCourses().getHasSelected()) {
                pageState.setIsCourseMode(false);
                pageState.getSections().resetSelected();
            } else {
                LOG.warn("Unknown action: {}.", action);
            }
        } catch (Exception e) {
            // ... or ignore
            LOG.warn("Something went wrong. Exception: {}", e.getMessage());
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(navState.getJspPage());
        dispatcher.forward(request, response);
    }
}
