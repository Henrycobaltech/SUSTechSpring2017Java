package com.sustech.flightbooking.infrastructure;

import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FlightBookingAuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        FlightBookingAuthenticationToken authentication =
                (FlightBookingAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getServletPath();
        if (needsAuthorize(authentication, path, "passenger", "passenger")
                || needsAuthorize(authentication, path, "manage", "administrator")
                || needsAuthorize(authentication, path, "changepassword", "passenger", "administrator")) {
            response.sendRedirect("/login?returnUri=" + path);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean needsAuthorize(FlightBookingAuthenticationToken authentication,
                                   String path, String requestPath, String... roles) {
        if (path.toLowerCase().startsWith("/" + requestPath)) {
            if (authentication == null) {
                return true;
            }
            for (String role : roles) {
                if (authentication.getRole().equalsIgnoreCase(role)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
