package com.sustech.flightbooking.domainmodel;

import java.util.UUID;

/**
 * Created by Henry on 4/17/2017.
 */

public abstract class FlightBookingUser extends EntityBase {
    private String userName;

    private String passwordHash;

    public FlightBookingUser(UUID id) {
        super(id);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean authenticate(String password) {
        return false;
    }
}
