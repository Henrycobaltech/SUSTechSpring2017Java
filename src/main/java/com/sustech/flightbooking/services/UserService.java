package com.sustech.flightbooking.services;

import com.sustech.flightbooking.domainmodel.FlightBookingUser;
import com.sustech.flightbooking.domainmodel.Passenger;

public interface UserService {
    boolean isUserNameRegisteredFor(FlightBookingUser user, String userName);

    boolean isIdCardRegisteredFor(Passenger user, String idCard);
}
