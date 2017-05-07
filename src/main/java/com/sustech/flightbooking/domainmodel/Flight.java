package com.sustech.flightbooking.domainmodel;

import org.mongodb.morphia.annotations.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Flight extends EntityBase {

    private String flightId;
    private double price;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String origin;
    private String destination;
    private int capacity;
    private boolean isPublished;
    private boolean isDeleted;

    public Flight(UUID id, String flightId, double price,
                  String origin, String destination,
                  LocalDateTime departureTime, LocalDateTime arrivalTime,
                  int capacity) {
        super(id);
        this.flightId = flightId;
        this.price = price;
        this.origin = origin;
        this.destination = destination;
        this.setPrice(price);
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.setCapacity(capacity);
    }

    public Flight() {
    }

    public double getPrice() {
        return price;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void publish() {
        this.isPublished = true;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
