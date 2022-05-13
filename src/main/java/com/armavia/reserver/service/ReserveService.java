package com.armavia.reserver.service;

import com.armavia.reserver.model.Reservation;

import java.util.List;

public interface ReserveService {
    Reservation getReservation(int customerId, int flightId);
    Reservation createReservation(int customerId, int flightId, int checkedBags, int price) throws Exception;
    Reservation updateReservation(int reservationId, int price, int checkedBags) throws Exception;
    Reservation deleteReservation(int reservationId) throws Exception;
}
