package com.sustech.flightbooking.persistence;

import com.sustech.flightbooking.domainmodel.Flight;
import com.sustech.flightbooking.domainmodel.Order;
import com.sustech.flightbooking.domainmodel.Passenger;

import java.util.List;

public interface OrderRepository extends Repository<Order> {
    List<Order> findByFlight(Flight flight);

    List<Order> findByPassenger(Passenger passenger);
}
