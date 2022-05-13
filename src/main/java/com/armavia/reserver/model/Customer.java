package com.armavia.reserver.model;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;

@Data
@Builder
public class Customer {
    private int customerId;
    private HashSet<Integer> reservations;
}
