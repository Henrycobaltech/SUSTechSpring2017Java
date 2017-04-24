package com.sustech.flightbooking.persistence.Impl;

import com.sustech.flightbooking.domainmodel.Administrator;
import com.sustech.flightbooking.persistence.AdministratorsRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Henry on 4/24/2017.
 */

@Repository
public class AdministratorRepositoryImpl extends RepositoryImplBase<Administrator>
        implements AdministratorsRepository {

    @Autowired
    public AdministratorRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    protected Class<Administrator> getGenericType() {
        return Administrator.class;
    }

    @Override
    public Administrator findByUserName(String userName) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Optional<Administrator> administrator = createQuery(session).stream()
                .filter(a -> a.getUserName().equalsIgnoreCase(userName)).findFirst();

        session.getTransaction().commit();
        session.close();
        return administrator.orElse(null);
    }


}
