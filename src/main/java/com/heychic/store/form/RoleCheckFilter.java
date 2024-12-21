package com.heychic.store.form;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RoleCheckFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Cast to HttpServletRequest and HttpServletResponse
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

            // Define restricted paths for admin
            List<String> restrictedPaths = List.of("/store", "/shopping-cart/**", "/my-profile", "/my-profile", "/my-orders", "/my-address", "/update-user-address", "/new-user", "/update-user-info", "/order-detail", "/checkout", "/product-detail");

            // Check if the request URI matches any restricted path
            String requestURI = httpRequest.getRequestURI();
            boolean isRestricted = restrictedPaths.stream().anyMatch(requestURI::startsWith);

            if (isAdmin && isRestricted) {
                // Logout admin and redirect to login page
                new SecurityContextLogoutHandler().logout(httpRequest, httpResponse, authentication);
                httpResponse.sendRedirect("/?error=unauthorized");
                return;
            }
        }

        // Continue the filter chain
        chain.doFilter(request, response);
    }
}

