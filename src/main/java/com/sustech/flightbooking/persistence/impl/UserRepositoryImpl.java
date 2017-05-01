package com.sustech.flightbooking.persistence.impl;

import com.sustech.flightbooking.domainmodel.FlightBookingUser;
import com.sustech.flightbooking.persistence.UserRepository;
import org.mongodb.morphia.Datastore;

public abstract class UserRepositoryImpl<T extends FlightBookingUser>
        extends RepositoryImplBase<T>
        implements UserRepository<T> {


    public UserRepositoryImpl(Datastore datastore) {
        super(datastore);
    }

    @Override
    public T findByUserName(String userName) {
        return datastore.createQuery(getEntityType()).field("userName").equalIgnoreCase(userName).get();
    }
}
