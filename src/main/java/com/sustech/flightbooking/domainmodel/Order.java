package com.sustech.flightbooking.domainmodel;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Order extends EntityBase {


    private int seat;

    @Reference
    private Flight flight;
    private LocalDateTime createdTime;
    private boolean isPaid;
    private boolean isCancelled;
    private double price;

    @Reference
    private Passenger passenger;

    public Order(UUID id, Flight flight, Passenger passenger, int seat) {
        super(id);
        this.flight = flight;
        this.passenger = passenger;
        this.createdTime = LocalDateTime.now();
        this.seat = seat;
    }

    public Order() {
    }

    public int getSeat() {
        return seat;
    }

    public Flight getFlight() {
        return flight;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void pay() {
        this.price = this.flight.getPrice();
        this.isPaid = true;
    }

    public void cancel() {
        this.isCancelled = true;
    }

    public double getPrice() {
        if (this.isPaid()) {
            return this.price;
        } else {
            return this.flight.getPrice();
        }
    }

    public OrderStatus getStatus() {
        if (this.isCancelled()) {
            return OrderStatus.CANCELLED;
        }
        if (this.isPaid()) {
            return OrderStatus.PAID;
        }
        if (this.getFlight().getDepartureTime().isBefore(LocalDateTime.now())) {
            return OrderStatus.EXPIRED;
        } else {
            return OrderStatus.UNPAID;
        }
    }
}
