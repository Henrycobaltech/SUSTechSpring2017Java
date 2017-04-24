package com.sustech.flightbooking.persistence.impl;

import com.sustech.flightbooking.domainmodel.EntityBase;
import com.sustech.flightbooking.persistence.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Created by Henry on 4/24/2017.
 */
public abstract class RepositoryImplBase<T extends EntityBase> implements Repository<T> {

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

    }

    @Override
    public void delete(T entity) {

    }
}
