package com.sustech.flightbooking.infrastructure;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

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
