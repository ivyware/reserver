package com.armavia.reserver;

import com.armavia.reserver.controller.ReservationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.text.MessageFormat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationApplicationTests {

    @Autowired
    ReservationController controller;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void testCreateReservation(){
        var custId = 2;
        var flightId = 3;
        var checkedBags = 4;
        var price = 5;
        var res = controller.reserveFlight(custId, flightId, checkedBags, price);

        assertThat(res.getCustomerId()).isEqualTo(custId);
        assertThat(res.getFlightId()).isEqualTo(flightId);
        assertThat(res.getCheckedBags()).isEqualTo(checkedBags);
        assertThat(res.getPrice()).isEqualTo(price);

        var custId2 = 3;
        var res2 = controller.reserveFlight(custId2, flightId, checkedBags, price);
        assertThat(res2.getSeatNumber()).isEqualTo(res.getSeatNumber()+1);
    }

    @Test
    public void testChangeReservation(){
        var custId = 4;
        var flightId = 3;
        var price1 = 5;
        var checkedBags1 = 4;
        var res1 = controller.reserveFlight(custId, flightId, price1, checkedBags1);

        var price2 = 9;
        var checkedBags2 = 7;
        var res2 = controller.changeReservation(res1.getReservationId(), price2, checkedBags2);

        assertThat(res2.getCustomerId()).isEqualTo(custId);
        assertThat(res2.getFlightId()).isEqualTo(flightId);
        assertThat(res2.getPrice()).isEqualTo(price2);
        assertThat(res2.getCheckedBags()).isEqualTo(checkedBags2);
    }

    @Test
    public void testCreationDuplicateReservation() throws Exception {
        var custId = 1;
        var flightId = 3;
        var price = 5;
        var checkedBags = 4;

        controller.reserveFlight(custId, flightId, checkedBags, price);
        var request = MessageFormat.format("/reserveFlight?customerId={0}&flightId={1}&checkedBags={2}&price={3}",
                custId, flightId, checkedBags, price);

        mockMvc.perform(post(request)).andExpect(status().isNotAcceptable());
    }

    @Test
    public void testDeletionReservation(){
        var custId = 5;
        var flightId = 3;
        var checkedBags = 4;
        var price = 5;

        var res = controller.reserveFlight(custId, flightId, checkedBags, price);
        var deleted = controller.deleteReservation(res.getReservationId());
        var nonexistant = controller.getReservation(custId, flightId);

        assertThat(nonexistant).isNull();

        assertThat(res.getCustomerId()).isEqualTo(deleted.getCustomerId());
        assertThat(res.getFlightId()).isEqualTo(deleted.getFlightId());
        assertThat(res.getCheckedBags()).isEqualTo(deleted.getCheckedBags());
        assertThat(res.getPrice()).isEqualTo(deleted.getPrice());
    }
}