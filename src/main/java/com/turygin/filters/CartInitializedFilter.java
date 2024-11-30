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

/**
 * Web filter that ensures that cart requests first go through the main cart servlet. This ensures
 * that the information about courses and sections in the cart has been initialized.
 */
@WebFilter(urlPatterns = {"/cart/select", "/cart/edit"})
public class CartInitializedFilter implements Filter {

    private static final Logger LOG = LogManager.getLogger(CartInitializedFilter.class);

    /**
     * Redirects users to the main cart servlet if cart page state has not yet been initialized.
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
