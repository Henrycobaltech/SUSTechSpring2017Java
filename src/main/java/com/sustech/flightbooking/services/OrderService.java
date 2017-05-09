package com.sustech.flightbooking.services;

import com.sustech.flightbooking.domainmodel.Order;
import com.sustech.flightbooking.domainmodel.OrderStatus;

public interface OrderService {
    OrderStatus getStatus(Order order);
}
