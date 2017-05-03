package com.sustech.flightbooking.persistence;

import com.sustech.flightbooking.domainmodel.Flight;
import com.sustech.flightbooking.domainmodel.Order;

public interface OrderRepository extends Repository<Order> {
    int countByFlight(Flight flight);
}
