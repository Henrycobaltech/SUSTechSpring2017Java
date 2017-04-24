package com.sustech.flightbooking.domainmodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by Henry on 4/17/2017.
 */

@Entity
public class Order extends EntityBase {

    @Column
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
