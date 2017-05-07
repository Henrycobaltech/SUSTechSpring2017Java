package com.sustech.flightbooking.services.impl;

import com.sustech.flightbooking.domainmodel.Flight;
import com.sustech.flightbooking.domainmodel.FlightStatus;
import com.sustech.flightbooking.persistence.OrderRepository;
import com.sustech.flightbooking.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private final OrderRepository orderRepository;

    @Autowired
    public FlightServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public FlightStatus getStatus(Flight flight) {
        if (flight.isDeleted())
            return FlightStatus.DELETED;
        if (!flight.isPublished())
            return FlightStatus.UNPUBLISHED;
        if (LocalDateTime.now().plusHours(2).isAfter(flight.getDepartureTime()))
            return FlightStatus.TERMINATE;
        if (orderRepository.countByFlight(flight) >= flight.getCapacity())
            return FlightStatus.FULL;
        else
            return FlightStatus.AVAILABLE;
    }

    @Override
    public List<String> validate(Flight flight) {
        List<String> errorMessages = new ArrayList<>();

        return errorMessages;
    }
}
