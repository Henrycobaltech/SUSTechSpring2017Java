package com.sustech.flightbooking.persistence.impl;

import com.sustech.flightbooking.domainmodel.Administrator;
import com.sustech.flightbooking.persistence.AdministratorsRepository;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Henry on 4/24/2017.
 */

@Repository
public class AdministratorRepositoryImpl extends UserRepositoryImpl<Administrator>
        implements AdministratorsRepository {

    @Autowired
    public AdministratorRepositoryImpl(Datastore datastore) {
        super(datastore);
    }
}
