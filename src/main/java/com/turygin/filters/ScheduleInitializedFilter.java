package com.turygin.filters;

import com.turygin.states.SchedulePageState;
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
 * Web filter that ensures that schedule requests first go through the main schedule servlet. This ensures
 * that schedules were generated.
 */
@WebFilter(urlPatterns = {"/schedule/switch"})
public class ScheduleInitializedFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger(ScheduleInitializedFilter.class);

    /**
     * Redirects users to the main schedule servlet if schedule page state has not yet been initialized.
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

        SchedulePageState pageState = (SchedulePageState) session.getAttribute("schedulePage");
        if (pageState == null) {
            LOG.debug("Schedule page was not initialized.");
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect(String.format("%s/%s", httpRequest.getContextPath(),
                    NavigationState.SCHEDULE.getDefaultServlet()));
        } else {
            chain.doFilter(request, response);
        }
    }
}
