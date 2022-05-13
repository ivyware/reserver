package com.armavia.reserver.service.ReserveServiceImpl;

import com.armavia.reserver.model.Customer;
import com.armavia.reserver.model.Flight;
import com.armavia.reserver.model.Reservation;
import com.armavia.reserver.repository.FlightRepository;
import com.armavia.reserver.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Service
public class ReserveServiceImpl implements ReserveService {

    @Autowired
    private FlightRepository flightRepository;

    @Override
    public Reservation getReservation(int customerId, int flightId){
        return flightRepository.getReservations(flightId).get(customerId);
    }

    @Override
    public Reservation createReservation(int customerId, int flightId, int checkedBags, int price) throws Exception {
        HashMap<Integer, Flight> flights = flightRepository.getFlights();
        Flight flight = flights.get(flightId);
        HashMap<Integer, Reservation> passengerReservations = flight.getPassengerReservations();

        if(passengerReservations.containsKey(customerId))
            throw new Exception("Customer already booked this flight");

        List<Integer> emptySeats = flight.getEmptySeats();

        if(emptySeats.size() == 0)
            throw new Exception("Flight is already full");

        HashMap<Integer, Customer> customers = flightRepository.getCustomers();

        int seatId = emptySeats.remove(0);

        var reservationId = flightRepository.getNextReservationId();
        var reservation = Reservation.builder()
                .reservationId(reservationId)
                .customerId(customerId)
                .flightId(flightId)
                .price(price)
                .seatNumber(seatId)
                .checkedBags(checkedBags)
                .bookingDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        if(!customers.containsKey(customerId))
            customers.put(customerId, Customer.builder()
                    .customerId(customerId)
                    .reservations(new HashSet<>())
                    .build());

        customers.get(customerId).getReservations().add(reservationId);

        flightRepository.addReservation(reservation);

        return reservation;
    }

    @Override
    public Reservation updateReservation(int reservationId, int price, int checkedBags) throws Exception {
        return flightRepository.updateReservation(reservationId, price, checkedBags);
    }

    @Override
    public Reservation deleteReservation(int reservationId) throws Exception {
        Reservation reservation = flightRepository.deleteReservation(reservationId);
        flightRepository.addEmptySeat(reservation.getFlightId(), reservation.getSeatNumber());

        HashMap<Integer, Customer> customers = flightRepository.getCustomers();
        customers.get(reservation.getCustomerId()).getReservations().remove(reservationId);

        return reservation;
    }
}
