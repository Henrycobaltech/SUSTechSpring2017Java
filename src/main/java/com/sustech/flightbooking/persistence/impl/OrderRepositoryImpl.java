package com.sustech.flightbooking.persistence.impl;

import com.sustech.flightbooking.domainmodel.Flight;
import com.sustech.flightbooking.domainmodel.Order;
import com.sustech.flightbooking.persistence.OrderRepository;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl extends RepositoryImplBase<Order> implements OrderRepository {

    @Autowired
    public OrderRepositoryImpl(Datastore datastore) {
        super(datastore);
    }

    @Override
    public int countByFlight(Flight flight) {
        return (int) datastore.createQuery(Order.class).asList().stream()
                .filter(o -> o.getFlight().equals(flight))
                .count();
    }

    @Override
    protected Class<Order> getEntityType() {
        return Order.class;
    }
}
