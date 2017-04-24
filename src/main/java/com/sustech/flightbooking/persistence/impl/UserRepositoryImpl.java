package com.sustech.flightbooking.persistence.impl;

import com.sustech.flightbooking.domainmodel.Administrator;
import com.sustech.flightbooking.domainmodel.Flight;
import com.sustech.flightbooking.domainmodel.FlightBookingUser;
import com.sustech.flightbooking.persistence.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

/**
 * Created by Henry on 4/24/2017.
 */
public abstract class UserRepositoryImpl<T extends FlightBookingUser>
        extends RepositoryImplBase<T>
        implements UserRepository<T> {

    public UserRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public T findByUserName(String userName) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Optional<T> user = createQuery(session).stream()
                .filter(u -> u.getUserName().equalsIgnoreCase(userName)).findFirst();

        session.getTransaction().commit();
        session.close();
        return user.orElse(null);
    }
}
