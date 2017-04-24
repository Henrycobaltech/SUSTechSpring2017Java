package com.sustech.flightbooking.persistence;

import com.sustech.flightbooking.domainmodel.FlightBookingUser;

/**
 * Created by Henry on 4/24/2017.
 */
public interface UserRepository<T extends FlightBookingUser>
        extends Repository<T> {

    T findByUserName(String userName);
}
