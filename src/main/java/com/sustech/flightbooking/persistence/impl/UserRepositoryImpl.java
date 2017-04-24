package com.sustech.flightbooking.persistence.impl;

import com.sustech.flightbooking.domainmodel.FlightBookingUser;
import com.sustech.flightbooking.persistence.UserRepository;

/**
 * Created by Henry on 4/24/2017.
 */
public abstract class UserRepositoryImpl<T extends FlightBookingUser>
        extends RepositoryImplBase<T>
        implements UserRepository<T> {


    @Override
    public T findByUserName(String userName) {
        return null;
    }
}
