package com.turygin.servlet.cart;

import com.turygin.api.client.RestClient;
import com.turygin.api.model.CourseWithSectionsDTO;

import java.io.*;
import java.util.List;

import com.turygin.states.nav.NavigationState;
import com.turygin.states.UserState;
import com.turygin.states.ViewCartPageState;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;


/**
 * Main cart servlet. Loads courses with sections from REST API to display on the cart page.
 */
@WebServlet(
        name = "ViewCart",
        urlPatterns = { "/cart/view" }
)
public class ViewCart extends HttpServlet {

    /** Empty constructor. */
    public ViewCart() {}

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

        // Web filter ensures that user has been logged in.
        UserState user = (UserState) session.getAttribute("userState");

        // Set navigation state
        NavigationState navState = NavigationState.CART;
        session.setAttribute("navState", navState);

        // Initialize page state
        ViewCartPageState pageState = new ViewCartPageState();

        // Fetch REST API client from the context
        ServletContext context = getServletContext();
        RestClient client = (RestClient) context.getAttribute("restClient");

        // Fetch cart course info from API
        try (Response apiResponse = client.cartGetCourses(user.getUserId())) {
            if (RestClient.isStatusSuccess(apiResponse)) {
                List<CourseWithSectionsDTO> courses = apiResponse.readEntity(new GenericType<>() {
                });
                pageState.setCourses(courses);
            } else {
                request.setAttribute("error", RestClient.getErrorMessage(apiResponse));
            }
        }

        // Save page state in session
        session.setAttribute("viewCartPage", pageState);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(navState.getJspPage());
        dispatcher.forward(request, response);
    }
}
