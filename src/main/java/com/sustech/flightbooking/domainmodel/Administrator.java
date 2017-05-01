package com.sustech.flightbooking.domainmodel;

import org.mongodb.morphia.annotations.Entity;

import java.util.UUID;

@Entity
public class Administrator extends FlightBookingUser {

    public Administrator() {
    }

    public Administrator(UUID id) {
        super(id);
    }

    @Override
    public String getRole() {
        return Administrator.class.getSimpleName();
    }
}
