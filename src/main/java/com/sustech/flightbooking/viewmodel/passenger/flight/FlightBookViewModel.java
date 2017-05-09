package com.sustech.flightbooking.viewmodel.passenger.flight;

import java.util.UUID;

public class FlightBookViewModel {
    private UUID flightId;
    private int seat;

    public UUID getFlightId() {
        return flightId;
    }

    public void setFlightId(UUID flightId) {
        this.flightId = flightId;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }
}
