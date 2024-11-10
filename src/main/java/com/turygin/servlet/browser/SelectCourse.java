package com.turygin.servlet.browser;

import com.turygin.states.BrowseCoursesPageState;

import com.turygin.states.nav.NavigationState;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Sample servlet that fetches information about all courses form the
 * REST API and sends it to JSP for display.
 */
@WebServlet(
        name = "BrowserSelectCourse",
        urlPatterns = { "/browser/select" }
)
public class SelectCourse extends HttpServlet {

    /** Empty constructor. */
    public SelectCourse() {}

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

        // Get page state
        BrowseCoursesPageState pageState = (BrowseCoursesPageState) session.getAttribute("browseCoursesPage");

        if (pageState == null) {
            // Should not be here yet. Course list was not initialized.
            response.sendRedirect(String.format("%s/%s", request.getContextPath(), navState.getDefaultServlet()));
            return;
        }

        try {
            // Select a course from the list if the index is valid ...
            int courseListId = Integer.parseInt(request.getParameter("courseListId"));
            pageState.setSelectedCourse(pageState.getLoadedCourses().get(courseListId));
        } catch (Exception e) {
            // ... or remove selection
            pageState.setSelectedCourse(null);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(navState.getJspPage());
        dispatcher.forward(request, response);
    }
}
