package com.sustech.flightbooking.services.impl;

import com.sustech.flightbooking.domainmodel.Administrator;
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
    public boolean isUserNameRegisteredFor(FlightBookingUser user, String userName) {
        Passenger passenger = passengerRepository.findByUserName(userName);
        if ((passenger != null) && !passenger.equals(user)) {
            return true;
        }
        Administrator admin = adminRepository.findByUserName(userName);
        return admin != null && !admin.equals(user);
    }

    @Override
    public boolean isIdCardRegisteredFor(Passenger user, String idCard) {
        Passenger userFound = passengerRepository.findByIdCard(idCard);
        return userFound != null && !userFound.equals(user);
    }
}