package com.sustech.flightbooking.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by Henry on 4/25/2017.
 */
public class FlightBookingAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        FlightBookingAuthenticationToken token = (FlightBookingAuthenticationToken) authentication;
        token.setAuthenticated(token.getUser() != null);
        SecurityContextHolder.getContext().setAuthentication(token);
        return token;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FlightBookingAuthenticationToken.class.equals(aClass);
    }
}
