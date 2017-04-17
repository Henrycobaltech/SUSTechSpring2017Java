package com.sustech.flightbooking.domainmodel;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by Henry on 4/17/2017.
 */
public class Order {
    private UUID id;
    private int seat;
    private Flight flight;
    private LocalDateTime timeStamp;
    private OrderStatus status;

    public UUID getId() {
        return id;
    }

    public int getSeat() {
        return seat;
    }

    public Flight getFlight() {
        return flight;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
