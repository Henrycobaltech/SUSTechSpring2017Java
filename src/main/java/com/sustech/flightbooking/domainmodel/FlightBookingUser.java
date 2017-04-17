package com.sustech.flightbooking.domainmodel;

import java.util.UUID;

/**
 * Created by Henry on 4/17/2017.
 */
public abstract class FlightBookingUser {
    private UUID id;
    private String userName;
    private String passwordHash;

    public FlightBookingUser(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
