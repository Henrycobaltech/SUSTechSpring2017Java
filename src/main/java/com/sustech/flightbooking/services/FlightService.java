package com.sustech.flightbooking.services;

import com.sustech.flightbooking.domainmodel.Flight;
import com.sustech.flightbooking.domainmodel.FlightStatus;

public interface FlightService {
    FlightStatus getStatus(Flight flight);
}
