package com.sustech.flightbooking.services.impl;

import com.sustech.flightbooking.infrastructure.FlightBookingAuthenticationToken;
import com.sustech.flightbooking.domainmodel.Administrator;
import com.sustech.flightbooking.domainmodel.FlightBookingUser;
import com.sustech.flightbooking.domainmodel.Passenger;
import com.sustech.flightbooking.persistence.AdministratorsRepository;
import com.sustech.flightbooking.persistence.PassengerRepository;
import com.sustech.flightbooking.services.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by Henry on 4/25/2017.
 */
@Service
public class IdentityServiceImpl implements IdentityService {

    private final PassengerRepository passengerRepository;
    private final AdministratorsRepository administratorsRepository;

    @Autowired
    public IdentityServiceImpl(PassengerRepository passengerRepository,
                               AdministratorsRepository administratorsRepository) {
        this.passengerRepository = passengerRepository;
        this.administratorsRepository = administratorsRepository;
    }

    @Override
    public FlightBookingAuthenticationToken login(String userName, String password) {
        Administrator admin = administratorsRepository.findByUserName(userName);
        if (admin != null) {
            if (admin.authenticate(password))
                return loginUser(admin);
        } else {
            Passenger passenger = passengerRepository.findByUserName(userName);
            if (passenger != null && passenger.authenticate(password))
                return loginUser(passenger);
        }
        return null;
    }

    @Override
    public FlightBookingUser getCurrentUser() {
        FlightBookingAuthenticationToken token =
                (FlightBookingAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return token.getUser();
    }

    private FlightBookingAuthenticationToken loginUser(FlightBookingUser user) {
        FlightBookingAuthenticationToken token = new FlightBookingAuthenticationToken(user);
        token.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(token);
        return token;
    }

    @Override
    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
