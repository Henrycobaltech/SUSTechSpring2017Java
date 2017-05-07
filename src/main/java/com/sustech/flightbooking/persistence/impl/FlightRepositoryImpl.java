package com.sustech.flightbooking.persistence.impl;

import com.sustech.flightbooking.domainmodel.Flight;
import com.sustech.flightbooking.persistence.FlightRepository;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FlightRepositoryImpl extends RepositoryImplBase<Flight> implements FlightRepository {

    @Autowired
    public FlightRepositoryImpl(Datastore datastore) {
        super(datastore);
    }

    @Override
    protected Class<Flight> getEntityType() {
        return Flight.class;
    }
}
