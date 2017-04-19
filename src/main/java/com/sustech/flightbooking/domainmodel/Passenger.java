package com.sustech.flightbooking.domainmodel;

import javax.persistence.Entity;
import java.util.UUID;

/**
 * Created by Henry on 4/17/2017.
 */

@Entity
public class Passenger extends FlightBookingUser {

    public Passenger(UUID id) {
        super(id);
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
