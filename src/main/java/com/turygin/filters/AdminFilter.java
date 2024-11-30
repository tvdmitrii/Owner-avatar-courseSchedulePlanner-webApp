package com.turygin.filters;

import com.turygin.states.UserState;
import com.turygin.states.nav.NavigationState;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Web filter that limits access to users with administrator privileges.
 */
@WebFilter(urlPatterns = {"/catalog/*"})
public class AdminFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger(AdminFilter.class);

    /** Servlet context for passing errors upon redirect to other pages. */
    private ServletContext context;

    /**
     * Save servlet context for later use.
     * @param filterConfig filter configuration object
     */
    @Override
    public void init(FilterConfig filterConfig) {
        // Obtain the ServletContext from FilterConfig
        context = filterConfig.getServletContext();
    }

    /**
     * Redirects users with insufficient permissions to the home page. Sets authError servlet context attribute to
     * indicate the error.
     * @param request object that contains the client's request information
     * @param response object used to send the response back to the client
     * @param chain chain of downstream web filters
     * @throws ServletException if servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();

        UserState user = (UserState) session.getAttribute("userState");
        if (user == null || !user.getIsAdmin()) {
            LOG.debug("Not logged in or not an admin, redirecting to home.");
            context.setAttribute("authError", "You must be an administrator to access this page!");
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect(String.format("%s/%s", httpRequest.getContextPath(), NavigationState.HOME));
        } else {
            chain.doFilter(request, response);
        }
    }
}
