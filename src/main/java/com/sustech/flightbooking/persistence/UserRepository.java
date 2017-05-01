package com.sustech.flightbooking.persistence;

import com.sustech.flightbooking.domainmodel.FlightBookingUser;

public interface UserRepository<T extends FlightBookingUser>
        extends Repository<T> {

    T findByUserName(String userName);
}
