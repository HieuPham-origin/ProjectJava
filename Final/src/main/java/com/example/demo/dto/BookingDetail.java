package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BookingDetail {
    private ContactDetail contactDetail;
    private List<PassengerDTO> passengerDTOS;
    private List<Integer> baggageIds1;
    private List<Integer> baggageIds2;
    private boolean hasExtraBaggage1 = false;
    private boolean hasExtraBaggage2 = false;
    private List<Integer> serviceIds1;
    private List<Integer> serviceIds2;
    private boolean hasExtraService1 = false;
    private boolean hasExtraService2 = false;
    private List<Integer> seatDetailIds1;
    private List<Integer> seatDetailIds2;
    private boolean hasChosenSeat1 = false;
    private boolean hasChosenSeat2 = false;
}
