package com.sustech.flightbooking.persistence.impl;

import com.sustech.flightbooking.domainmodel.EntityBase;
import com.sustech.flightbooking.persistence.Repository;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

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

    protected abstract Class<T> getEntityType();

    @Override
    public List<T> findAll() {
        return datastore.find(getEntityType()).asList();
    }

    @Override
    public T findById(UUID id) {
        return datastore.find(getEntityType()).field("id").equal(id).get();
    }

    @Override
    public void save(T entity) {
        datastore.save(entity);
    }

    @Override
    public void delete(T entity) {
        datastore.delete(entity);
    }
}
