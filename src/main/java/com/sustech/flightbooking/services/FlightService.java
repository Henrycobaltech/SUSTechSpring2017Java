package com.sustech.flightbooking.services;

import com.sustech.flightbooking.domainmodel.Flight;
import com.sustech.flightbooking.domainmodel.FlightStatus;
import com.sustech.flightbooking.domainmodel.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public interface FlightService {
    FlightStatus getStatus(Flight flight);

    List<String> validate(Flight flight);

    List<Order> getOrders(Flight flight);

    List<Integer> getAvailableSeats(Flight flight);

    Stream<Flight> searchFilter(Stream<Flight> flightStream, String city, String flightNumber, LocalDate departureDate);
}
