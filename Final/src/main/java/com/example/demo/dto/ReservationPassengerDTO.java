package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReservationPassengerDTO {
    private String name;
    private String seat1;
    private String seat2;
    private int ticket1Id;
    private int ticket2Id;
    private String baggage1;
    private String baggage2;


}
