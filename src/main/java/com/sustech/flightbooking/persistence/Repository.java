package com.sustech.flightbooking.persistence;

import com.sustech.flightbooking.domainmodel.EntityBase;

import java.util.List;
import java.util.UUID;

/**
 * Created by Henry on 4/23/2017.
 */
public interface Repository<T extends EntityBase> {
    List<T> findAll();

    T findById(UUID id);

    void save(T entity);

    void delete(T entity);
}
