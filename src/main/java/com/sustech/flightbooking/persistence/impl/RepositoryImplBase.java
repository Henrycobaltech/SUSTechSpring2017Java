package com.sustech.flightbooking.persistence.impl;

import com.sustech.flightbooking.domainmodel.EntityBase;
import com.sustech.flightbooking.persistence.Repository;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * Created by Henry on 4/24/2017.
 */
public abstract class RepositoryImplBase<T extends EntityBase> implements Repository<T> {

    protected final Datastore datastore;

    public RepositoryImplBase(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public List<T> findAll() {
        return null;
    }

    @Override
    public T findById(UUID id) {
        return null;
    }

    @Override
    public void save(T entity) {
        datastore.save(entity);
    }

    @Override
    public void delete(T entity) {

    }
}
