package com.sustech.flightbooking.persistence;

import com.sustech.flightbooking.domainmodel.EntityBase;

import java.util.List;
import java.util.UUID;

public interface Repository<T extends EntityBase> {
    List<T> findAll();

    T findById(UUID id);

    void save(T entity);

    void delete(T entity);
}
