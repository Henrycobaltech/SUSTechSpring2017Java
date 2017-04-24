package com.sustech.flightbooking.persistence.impl;

import com.sustech.flightbooking.domainmodel.Passenger;
import com.sustech.flightbooking.persistence.PassengerRepository;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Henry on 4/24/2017.
 */
@Repository
public class PassengerRepositoryImpl
        extends UserRepositoryImpl<Passenger>
        implements PassengerRepository {

    @Autowired
    public PassengerRepositoryImpl(Datastore datastore) {
        super(datastore);
    }
}
