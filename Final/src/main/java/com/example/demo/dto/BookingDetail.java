package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BookingDetail {
    private ContactDetail contactDetail;
    private List<PassengerDTO> passengerDTOS;
    private List<Integer> baggageIds1;
    private List<Integer> baggageIds2;
    private boolean hasExtraBaggage1 = false;
    private boolean hasExtraBaggage2 = false;
}
