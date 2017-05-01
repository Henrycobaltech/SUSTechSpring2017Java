package com.sustech.flightbooking.domainmodel;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class Order extends EntityBase {


    private int seat;

    @Reference
    private Flight flight;
    private Timestamp createdTime;
    private OrderStatus status;

    public Order(UUID id) {
        super(id);
    }

    public Order() {
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
