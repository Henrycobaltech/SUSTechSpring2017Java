package com.sustech.flightbooking.services;

import com.sustech.flightbooking.domainmodel.FlightBookingUser;
import com.sustech.flightbooking.domainmodel.Passenger;

public interface UserService {
    boolean isUserNameAvailableFor(FlightBookingUser user, String userName);

    boolean isIdCardAvailableFor(Passenger user, String idCard);
}
