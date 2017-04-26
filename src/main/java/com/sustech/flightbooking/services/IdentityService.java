package com.sustech.flightbooking.services;

import com.sustech.flightbooking.infrastructure.FlightBookingAuthenticationToken;
import com.sustech.flightbooking.domainmodel.FlightBookingUser;

/**
 * Created by Henry on 4/25/2017.
 */
public interface IdentityService {
    FlightBookingAuthenticationToken login(String userName, String password);

    FlightBookingUser getCurrentUser();

    void logout();
}
