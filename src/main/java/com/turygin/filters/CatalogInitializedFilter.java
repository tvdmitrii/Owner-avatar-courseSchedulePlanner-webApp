package com.turygin.filters;

import com.turygin.states.EditCatalogPageState;
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
 * Web filter that ensures that catalog requests first go through the main catalog servlet. This ensures
 * that the information about courses, sections, instructors, and departments has been initialized.
 */
@WebFilter(urlPatterns = {"/catalog/course", "/catalog/section", "/catalog/new", "/catalog/select", "/catalog/search"})
public class CatalogInitializedFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger(CatalogInitializedFilter.class);

    /**
     * Redirects users to the main catalog servlet if catalog page state has not yet been initialized.
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

        EditCatalogPageState pageState = (EditCatalogPageState) session.getAttribute("editCatalogPage");
        if (pageState == null) {
            LOG.debug("Course and section catalog is not initialized.");
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect(String.format("%s/%s", httpRequest.getContextPath(),
                    NavigationState.CATALOG.getDefaultServlet()));
        } else {
            chain.doFilter(request, response);
        }
    }
}
