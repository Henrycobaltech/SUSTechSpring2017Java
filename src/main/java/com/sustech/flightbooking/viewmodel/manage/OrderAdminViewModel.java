package com.sustech.flightbooking.viewmodel.manage;

import com.sustech.flightbooking.domainmodel.Order;
import com.sustech.flightbooking.domainmodel.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderAdminViewModel {
    private String passengerName;
    private String passengerIdCard;
    private int seat;
    private String flightNumber;
    private UUID flightId;
    private LocalDateTime creationTime;
    private OrderStatus status;
    private double price;

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

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

    public UUID getFlightId() {
        return flightId;
    }

    public void setFlightId(UUID flightId) {
        this.flightId = flightId;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPassengerIdCard() {
        return passengerIdCard;
    }

    public void setPassengerIdCard(String passengerIdCard) {
        this.passengerIdCard = passengerIdCard;
    }

    public static OrderAdminViewModel createFromDomainModel(Order order) {
        OrderAdminViewModel vm = new OrderAdminViewModel();
        vm.setCreationTime(order.getCreatedTime());
        vm.setSeat(order.getSeat());
        vm.setStatus(order.getStatus());
        vm.setPassengerName(order.getPassenger().getDisplayName());
        vm.setPassengerIdCard(order.getPassenger().getIdentityCardNumber());
        vm.setFlightId(order.getFlight().getId());
        vm.setFlightNumber(order.getFlight().getFlightNumber());
        vm.setPrice(order.getPrice());
        return vm;
    }
}
