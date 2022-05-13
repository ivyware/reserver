package com.armavia.reserver.model;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class Reservation {
    private int reservationId;
    private int customerId;
    private int flightId;
    private int price;
    private int checkedBags;
    private int seatNumber;
    private Timestamp bookingDate;
}
