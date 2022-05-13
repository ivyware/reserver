package com.armavia.reserver.repository.impl;

import com.armavia.reserver.model.Customer;
import com.armavia.reserver.model.Flight;
import com.armavia.reserver.model.Reservation;
import com.armavia.reserver.repository.FlightRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

@Repository
public class FlightRepositoryStubImpl implements FlightRepository {

    private HashMap<Integer, Flight> flights;
    private HashMap<Integer, Reservation> reservations;
    private HashMap<Integer, Customer> customers;
    private int nextReservationId;

    public FlightRepositoryStubImpl(){
        nextReservationId = 1;
        reservations = new HashMap<>();
        customers = new HashMap<>();
        populateFlights();
    }

    private void populateFlights() {
        flights = new HashMap<>();

        flights.put(1, Flight.builder()
                .flightId(1)
                .capacity(10)
                .emptySeats(getEmptySeats(10))
                .passengerReservations(new HashMap<>())
                .build());

        flights.put(2, Flight.builder()
                .flightId(2)
                .capacity(20)
                .emptySeats(getEmptySeats(20))
                .passengerReservations(new HashMap<>())
                .build());

        flights.put(3, Flight.builder()
                .flightId(3)
                .capacity(30)
                .emptySeats(getEmptySeats(30))
                .passengerReservations(new HashMap<>())
                .build());

        flights.put(4, Flight.builder()
                .flightId(4)
                .capacity(40)
                .emptySeats(getEmptySeats(40))
                .passengerReservations(new HashMap<>())
                .build());
    }

    private ArrayList<Integer> getEmptySeats(int n){
        var list = new ArrayList<Integer>();

        for(int i = 1; i <= n; i++) //passengers expect the first seat to be numbered 1
            list.add(i);

        return list;
    }

    @Override
    public HashMap<Integer, Customer> getCustomers(){
        return customers;
    }

    @Override
    public HashMap<Integer, Flight> getFlights(){
        return flights;
    }

    @Override
    public void addReservation(Reservation reservation) {
        var flightId = reservation.getFlightId();
        var customerId = reservation.getCustomerId();

        flights.get(flightId).getPassengerReservations().put(customerId, reservation);
        reservations.put(reservation.getReservationId(), reservation);
    }

    @Override
    public int getNextReservationId(){
        return nextReservationId++;
    }

    @Override
    public void addEmptySeat(int flightId, int seatNumber){
        flights.get(flightId).getEmptySeats().add(seatNumber);
    }

    @Override
    public HashMap<Integer, Reservation> getReservations(int flightId) {
        return flights.get(flightId).getPassengerReservations();
    }

    @Override
    public Reservation updateReservation(int reservationId, int price, int checkedBags) throws Exception {
        var reservation = reservations.get(reservationId);
        if(reservation == null)
            throw new Exception("Reservation not found");
        reservation.setPrice(price);
        reservation.setCheckedBags(checkedBags);

        return reservation;
    }

    @Override
    public Reservation deleteReservation(int reservationId) throws Exception {
        var deletedReservation = reservations.get(reservationId);
        if(deletedReservation == null)
            throw new Exception("Reservation not found");

        flights.get(deletedReservation.getFlightId())
                .getPassengerReservations()
                .remove(deletedReservation.getCustomerId());
        reservations.remove(reservationId);

        return deletedReservation;
    }
}
