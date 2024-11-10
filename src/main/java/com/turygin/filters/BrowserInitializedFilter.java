package com.turygin.filters;

import com.turygin.states.BrowseCoursesPageState;
import com.turygin.states.ViewCartPageState;
import com.turygin.states.nav.NavigationState;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter(urlPatterns = {"/browser/select", "/browser/search", "/browser/addToCart"})
public class BrowserInitializedFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger(BrowserInitializedFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();

        BrowseCoursesPageState pageState = (BrowseCoursesPageState) session.getAttribute("browseCoursesPage");
        if (pageState == null) {
            LOG.debug("Course list is not initialized.");
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect(String.format("%s/%s", httpRequest.getContextPath(),
                    NavigationState.BROWSER.getDefaultServlet()));
        } else {
            chain.doFilter(request, response);
        }
    }
}
