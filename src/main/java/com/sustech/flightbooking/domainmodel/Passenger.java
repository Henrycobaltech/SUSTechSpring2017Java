package com.sustech.flightbooking.domainmodel;

import org.mongodb.morphia.annotations.Entity;

import java.util.UUID;

@Entity
public class Passenger extends FlightBookingUser {

    public Passenger(UUID id) {
        super(id);
    }

    public Passenger() {
    }

    @Override
    public String getRole() {
        return Passenger.class.getSimpleName();
    }

    private String displayName;
    private String identityCardNumber;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getIdentityCardNumber() {
        return identityCardNumber;
    }

    public void setIdentityCardNumber(String identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
    }
}
