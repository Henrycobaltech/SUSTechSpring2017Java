package com.sustech.flightbooking.services.impl;

import com.sustech.flightbooking.domainmodel.Flight;
import com.sustech.flightbooking.domainmodel.FlightStatus;
import com.sustech.flightbooking.domainmodel.Order;
import com.sustech.flightbooking.domainmodel.OrderStatus;
import com.sustech.flightbooking.persistence.FlightRepository;
import com.sustech.flightbooking.persistence.OrderRepository;
import com.sustech.flightbooking.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class FlightServiceImpl implements FlightService {

    private final OrderRepository orderRepository;
    private final FlightRepository flightRepository;

    @Autowired
    public FlightServiceImpl(OrderRepository orderRepository, FlightRepository flightRepository) {
        this.orderRepository = orderRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    public FlightStatus getStatus(Flight flight) {
        if (flight.isDeleted())
            return FlightStatus.DELETED;
        if (LocalDateTime.now().plusHours(2).isAfter(flight.getDepartureTime()))
            return FlightStatus.TERMINATE;
        if (!flight.isPublished())
            return FlightStatus.UNPUBLISHED;
        if (orderRepository.findByFlight(flight).stream()
                .filter(o -> o.getStatus() != OrderStatus.CANCELLED)
                .count() >= flight.getCapacity())
            return FlightStatus.FULL;
        else
            return FlightStatus.AVAILABLE;
    }

    @Override
    public List<String> validate(Flight flight) {
        List<String> errorMessages = new ArrayList<>();
        if (flight.getArrivalTime().isBefore(flight.getDepartureTime()))
            errorMessages.add("ArrivalTime is before DepartureTime");
        if (flight.getDepartureTime().minusHours(2).isBefore(LocalDateTime.now()))
            errorMessages.add("DepartureTime should be two hours more than the current time");
        if (flight.getCapacity() < orderRepository.findByFlight(flight).size())
            errorMessages.add("The number of orders is beyond capacity");
        return errorMessages;
    }

    @Override
    public List<Order> getOrders(Flight flight) {
        return orderRepository.findByFlight(flight);
    }

    @Override
    public List<Integer> getAvailableSeats(Flight flight) {
        List<Integer> bookedSeats = orderRepository.findByFlight(flight).stream()
                .filter(o -> o.getStatus() != OrderStatus.CANCELLED)
                .map(Order::getSeat)
                .collect(Collectors.toList());
        int maxBookedSeats = bookedSeats.stream().mapToInt(x -> x).max().orElse(0);
        List<Integer> seatRange = IntStream.rangeClosed(1, Math.max(flight.getCapacity(), maxBookedSeats))
                .boxed().collect(Collectors.toList());
        seatRange.removeAll(bookedSeats);
        return seatRange.stream()
                .limit(flight.getCapacity() - bookedSeats.size())
                .collect(Collectors.toList());
    }

    @Override
    public Stream<Flight> search(String city, String flightNumber, LocalDate departureDate) {
        Stream<Flight> flightStream = flightRepository.findAll().stream();
        if (city != null && !city.isEmpty()) {
            flightStream = flightStream.filter(f -> f.getOrigin().toLowerCase().contains(city.toLowerCase())
                    || f.getDestination().toLowerCase().contains(city.toLowerCase()));
        }
        if (flightNumber != null && !flightNumber.isEmpty()) {
            flightStream = flightStream.filter(f -> f.getFlightNumber().toLowerCase().contains(flightNumber.toLowerCase()));
        }
        if (departureDate != null) {
            flightStream = flightStream.filter(f -> f.getDepartureTime().toLocalDate().equals(departureDate));
        }
        return flightStream;
    }
}
