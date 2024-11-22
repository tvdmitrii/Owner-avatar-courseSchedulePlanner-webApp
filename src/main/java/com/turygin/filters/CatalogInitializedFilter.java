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

@WebFilter(urlPatterns = {"/catalog/course", "/catalog/section", "/catalog/new", "/catalog/select"})
public class CatalogInitializedFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger(CatalogInitializedFilter.class);

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
