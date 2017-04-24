package com.sustech.flightbooking.persistence.impl;

import com.sustech.flightbooking.domainmodel.Passenger;
import com.sustech.flightbooking.persistence.PassengerRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 * Created by Henry on 4/24/2017.
 */
@Repository
public class PassengerRepositoryImpl
        extends UserRepositoryImpl<Passenger>
        implements PassengerRepository {
    public PassengerRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class<Passenger> getGenericType() {
        return Passenger.class;
    }
}
