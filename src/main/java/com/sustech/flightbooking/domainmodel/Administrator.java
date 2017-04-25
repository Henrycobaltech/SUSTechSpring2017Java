package com.sustech.flightbooking.domainmodel;

import org.mongodb.morphia.annotations.Entity;

import java.util.UUID;

/**
 * Created by Henry on 4/17/2017.
 */

@Entity
public class Administrator extends FlightBookingUser {

    public Administrator() {
    }

    public Administrator(UUID id) {
        super(id);
    }

    @Override
    public String getRole() {
        return Administrator.class.getName();
    }
}
