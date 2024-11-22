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

@WebFilter(urlPatterns = {"/catalog/*"})
public class AdminFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger(AdminFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();

        UserState user = (UserState) session.getAttribute("userState");
        if (user == null || !user.getIsAdmin()) {
            LOG.debug("Not logged in or not an admin, redirecting to home.");
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect(String.format("%s/%s", httpRequest.getContextPath(), NavigationState.HOME));
        } else {
            chain.doFilter(request, response);
        }
    }
}
