package com.sustech.flightbooking.services;

import com.sustech.flightbooking.domainmodel.Flight;
import com.sustech.flightbooking.domainmodel.FlightStatus;

import java.util.List;

public interface FlightService {
    FlightStatus getStatus(Flight flight);

    List<String> validate(Flight flight);
}
