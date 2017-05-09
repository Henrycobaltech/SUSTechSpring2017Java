package com.sustech.flightbooking.viewmodel.passenger.order;

import com.sustech.flightbooking.domainmodel.Order;
import com.sustech.flightbooking.domainmodel.OrderStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderPassengerViewModel {
    private UUID id;
    private int seat;
    private String flightNumber;
    private LocalDateTime creationTime;
    private double price;

    private OrderStatus status;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime departureTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime arrivalTime;

    private String origin;
    private String destination;

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static OrderPassengerViewModel createFromViewModel(Order order) {
        OrderPassengerViewModel vm = new OrderPassengerViewModel();

        vm.setStatus(order.getStatus());
        vm.setCreationTime(order.getCreatedTime());
        vm.setFlightNumber(order.getFlight().getFlightNumber());
        vm.setOrigin(order.getFlight().getOrigin());
        vm.setDestination(order.getFlight().getDestination());
        vm.setDepartureTime(order.getFlight().getDepartureTime());
        vm.setArrivalTime(order.getFlight().getArrivalTime());
        vm.setSeat(order.getSeat());
        vm.setPrice(order.getPrice());
        vm.setId(order.getId());

        return vm;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
