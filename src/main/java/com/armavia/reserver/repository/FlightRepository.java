package com.armavia.reserver.repository;

import com.armavia.reserver.model.Customer;
import com.armavia.reserver.model.Flight;
import com.armavia.reserver.model.Reservation;

import java.util.HashMap;

public interface FlightRepository {
    HashMap<Integer, Customer> getCustomers();

    HashMap<Integer, Flight> getFlights();

    void addReservation(Reservation reservation);

    int getNextReservationId();

    void addEmptySeat(int flightId, int seatNumber);

    HashMap<Integer, Reservation> getReservations(int flightId);

    Reservation updateReservation(int reservationId, int price, int checkedBags) throws Exception;

    Reservation deleteReservation(int reservationId) throws Exception;
}
