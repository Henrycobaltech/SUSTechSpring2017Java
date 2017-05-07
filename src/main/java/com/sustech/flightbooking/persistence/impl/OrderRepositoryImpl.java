package com.sustech.flightbooking.persistence.impl;

import com.sustech.flightbooking.domainmodel.Flight;
import com.sustech.flightbooking.domainmodel.Order;
import com.sustech.flightbooking.persistence.OrderRepository;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OrderRepositoryImpl extends RepositoryImplBase<Order> implements OrderRepository {

    @Autowired
    public OrderRepositoryImpl(Datastore datastore) {
        super(datastore);
    }

    @Override
    public List<Order> findByFlight(Flight flight) {
        return datastore.createQuery(Order.class).asList().stream()
                .filter(o -> o.getFlight().equals(flight))
                .collect(Collectors.toList());
    }

    @Override
    protected Class<Order> getEntityType() {
        return Order.class;
    }
}
