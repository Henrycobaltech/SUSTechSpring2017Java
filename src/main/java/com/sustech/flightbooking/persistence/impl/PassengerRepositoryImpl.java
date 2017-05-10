package com.sustech.flightbooking.persistence.impl;

import com.sustech.flightbooking.domainmodel.Passenger;
import com.sustech.flightbooking.persistence.PassengerRepository;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PassengerRepositoryImpl
        extends UserRepositoryImpl<Passenger>
        implements PassengerRepository {

    @Autowired
    public PassengerRepositoryImpl(Datastore datastore) {
        super(datastore);
    }

    @Override
    protected Class<Passenger> getEntityType() {
        return Passenger.class;
    }

    @Override
    public Passenger findByIdCard(String idCard) {
        return datastore.createQuery(Passenger.class).field("identityCardNumber").equal(idCard).get();
    }
}
