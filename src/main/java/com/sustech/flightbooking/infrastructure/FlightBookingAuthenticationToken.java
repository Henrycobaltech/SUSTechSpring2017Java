package com.sustech.flightbooking.infrastructure;

import com.sustech.flightbooking.domainmodel.Administrator;
import com.sustech.flightbooking.domainmodel.FlightBookingUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by Henry on 4/25/2017.
 */
public class FlightBookingAuthenticationToken implements Authentication {

    private boolean isAuthenticated;

    private final FlightBookingUser user;

    public FlightBookingAuthenticationToken(FlightBookingUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    public String getRole() {
        return user.getRole();
    }

    public FlightBookingUser getUser() {
        return user;
    }

    @Override
    public Object getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        isAuthenticated = b;
    }

    @Override
    public String getName() {
        return null;
    }
}
