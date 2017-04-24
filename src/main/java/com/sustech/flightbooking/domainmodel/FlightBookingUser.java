package com.sustech.flightbooking.domainmodel;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * Created by Henry on 4/17/2017.
 */
@MappedSuperclass
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
