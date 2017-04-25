package com.sustech.flightbooking.services;

import com.sustech.flightbooking.config.FlightBookingAuthenticationToken;

/**
 * Created by Henry on 4/25/2017.
 */
public interface IdentityService {
    FlightBookingAuthenticationToken login(String userName, String password);

    void logout();
}
