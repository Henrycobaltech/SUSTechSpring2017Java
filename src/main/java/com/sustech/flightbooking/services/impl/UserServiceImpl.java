package com.sustech.flightbooking.services.impl;

import com.sustech.flightbooking.domainmodel.FlightBookingUser;
import com.sustech.flightbooking.domainmodel.Passenger;
import com.sustech.flightbooking.persistence.AdministratorsRepository;
import com.sustech.flightbooking.persistence.PassengerRepository;
import com.sustech.flightbooking.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final PassengerRepository passengerRepository;
    private final AdministratorsRepository adminRepository;


    @Autowired
    public UserServiceImpl(PassengerRepository passengerRepository, AdministratorsRepository adminRepository) {
        this.passengerRepository = passengerRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public boolean isUserNameAvailableFor(FlightBookingUser user, String userName) {
        return !(passengerRepository.findByUserName(userName) != user
                || adminRepository.findByUserName(userName) != user);
    }

    @Override
    public boolean isIdCardAvailableFor(Passenger user, String idCard) {
        return passengerRepository.findByIdCard(idCard) == user;
    }
}