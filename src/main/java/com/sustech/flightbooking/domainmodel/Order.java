package com.sustech.flightbooking.domainmodel;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by Henry on 4/17/2017.
 */


public class Order extends EntityBase {


    private int seat;

    private Flight flight;
    private Timestamp createdTime;
    private OrderStatus status;

    public Order(UUID id) {
        super(id);
    }

    public int getSeat() {
        return seat;
    }

    public Flight getFlight() {
        return flight;
    }


    public OrderStatus getStatus() {
        return status;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }
}
