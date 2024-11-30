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
 * Servlet that selects a course from a course list on the course browser page.
 */
@WebServlet(
        name = "BrowserSelectCourse",
        urlPatterns = { "/browser/select" }
)
public class SelectCourse extends HttpServlet {

    /** Empty constructor. */
    public SelectCourse() {}

    /**
     * Handles HTTP GET requests which contains course list ID as a query parameter.
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

        // Web filter ensures that page state was initialized.
        BrowseCoursesPageState pageState = (BrowseCoursesPageState) session.getAttribute("browseCoursesPage");

        try {
            // Select a course from the list if the index is valid ...
            int courseListId = Integer.parseInt(request.getParameter("courseListId"));
            pageState.getCourses().setSelectedId(courseListId);
        } catch (Exception e) {
            // ... or remove selection
            pageState.getCourses().resetSelected();
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(navState.getJspPage());
        dispatcher.forward(request, response);
    }
}
