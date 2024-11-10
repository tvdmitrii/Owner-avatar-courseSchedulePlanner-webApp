package com.turygin.filters;

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

@WebFilter(urlPatterns = {"/cart/select", "/cart/edit"})
public class CartInitializedFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger(CartInitializedFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();

        ViewCartPageState pageState = (ViewCartPageState) session.getAttribute("viewCartPage");
        if (pageState == null) {
            LOG.debug("Cart was not initialized.");
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect(String.format("%s/%s", httpRequest.getContextPath(),
                    NavigationState.CART.getDefaultServlet()));
        } else {
            chain.doFilter(request, response);
        }
    }
}
