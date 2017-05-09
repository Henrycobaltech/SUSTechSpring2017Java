package com.sustech.flightbooking.services.impl;

import com.sustech.flightbooking.domainmodel.FlightStatus;
import com.sustech.flightbooking.domainmodel.Order;
import com.sustech.flightbooking.domainmodel.OrderStatus;
import com.sustech.flightbooking.services.FlightService;
import com.sustech.flightbooking.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final FlightService flightService;

    @Autowired
    public OrderServiceImpl(FlightService flightService) {
        this.flightService = flightService;
    }

    @Override
    public OrderStatus getStatus(Order order) {
        if (order.isCancelled()) {
            return OrderStatus.CANCELLED;
        }
        if (order.isPaid()) {
            return OrderStatus.PAID;
        }
        if (flightService.getStatus(order.getFlight()) == FlightStatus.TERMINATE
                && !order.isPaid()) {
            return OrderStatus.EXPIRED;
        } else {
            return OrderStatus.UNPAID;
        }
    }
}
