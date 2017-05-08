package com.sustech.flightbooking.domainmodel;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Order extends EntityBase {


    private int seat;

    @Reference
    private Flight flight;
    private LocalDateTime createdTime;
    private OrderStatus status;

    @Reference
    private Passenger passenger;

    public Order(UUID id, Passenger passenger) {
        super(id);
        this.passenger = passenger;
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

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public Passenger getPassenger() {
        return passenger;
    }
}
