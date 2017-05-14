package com.sustech.flightbooking.services;

public interface SetupService {
    boolean isSetup();

    void setup(boolean generateFlights);
}
