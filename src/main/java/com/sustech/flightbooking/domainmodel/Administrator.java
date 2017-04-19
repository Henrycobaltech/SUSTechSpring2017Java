package com.sustech.flightbooking.domainmodel;

import javax.persistence.Entity;
import java.util.UUID;

/**
 * Created by Henry on 4/17/2017.
 */

@Entity
public class Administrator extends FlightBookingUser {

    public Administrator(UUID id) {
        super(id);
    }
}
