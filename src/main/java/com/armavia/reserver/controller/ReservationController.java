package com.armavia.reserver.controller;

import com.armavia.reserver.model.Reservation;
import com.armavia.reserver.service.ReserveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
public class ReservationController {

    @Autowired
    private ReserveService reserveService;

    @RequestMapping(value = "/getReservation", method = RequestMethod.GET)
    @ResponseBody
    public Reservation getReservation(@RequestParam int customerId,
                                      @RequestParam int flightId){
        log.info("Received request for customerId: " + customerId + " and flightId:" + flightId);
        return reserveService.getReservation(customerId, flightId);
    }

    @RequestMapping(value = "/reserveFlight", method = RequestMethod.POST)
    @ResponseBody
    public Reservation reserveFlight(@RequestParam int customerId,
                                     @RequestParam int flightId,
                                     @RequestParam int checkedBags,
                                     @RequestParam int price){
        Reservation reservation;
        try {
             reservation = reserveService.createReservation(customerId, flightId, checkedBags, price);
             log.info("Reservation created:" + reservation);
             return reservation;
        } catch (Exception e) {
           throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
    }

    @RequestMapping(value = "/changeReservation", method = RequestMethod.PUT)
    @ResponseBody
    public Reservation changeReservation(@RequestParam int reservationId,
                                         @RequestParam int price,
                                         @RequestParam int checkedBags){
        Reservation reservation;
        try{
            reservation = reserveService.updateReservation(reservationId, price, checkedBags);
            log.info("Reservation changed:" + reservation);
            return reservation;
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @RequestMapping(value = "/deleteReservation", method = RequestMethod.DELETE)
    @ResponseBody
    public Reservation deleteReservation(@RequestParam int reservation_id){
        Reservation reservation;
        try{
            reservation = reserveService.deleteReservation(reservation_id);
            log.info("Reservation deleted:" + reservation);
            return reservation;
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}