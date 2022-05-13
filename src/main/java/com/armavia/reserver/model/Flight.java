package com.armavia.reserver.model;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
@Builder
public class Flight {
    private int flightId;
    private int capacity;
    private HashMap<Integer, Reservation> passengerReservations;
    private List<Integer> emptySeats;
}