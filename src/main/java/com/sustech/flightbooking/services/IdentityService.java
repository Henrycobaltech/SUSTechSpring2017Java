package com.sustech.flightbooking.services;

import com.sustech.flightbooking.infrastructure.FlightBookingAuthenticationToken;
import com.sustech.flightbooking.domainmodel.FlightBookingUser;

public interface IdentityService {
    FlightBookingAuthenticationToken login(String userName, String password);

    FlightBookingUser getCurrentUser();

    void logout();
}
