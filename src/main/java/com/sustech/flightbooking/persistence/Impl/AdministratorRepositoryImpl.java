package com.sustech.flightbooking.persistence.impl;

import com.sustech.flightbooking.domainmodel.Administrator;
import com.sustech.flightbooking.persistence.AdministratorsRepository;
import com.sustech.flightbooking.persistence.impl.UserRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Henry on 4/24/2017.
 */

@Repository
public class AdministratorRepositoryImpl extends UserRepositoryImpl<Administrator>
        implements AdministratorsRepository {

    @Autowired
    public AdministratorRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class<Administrator> getGenericType() {
        return Administrator.class;
    }
}
