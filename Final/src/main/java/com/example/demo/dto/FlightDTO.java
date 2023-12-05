package com.example.demo.dto;

import com.example.demo.entity.FlightPlane;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@Getter @Setter
public class FlightDTO {
    private FlightPlane flightPlane;
    private String duration;

    public FlightDTO(FlightPlane flightPlane) {
        this.flightPlane = flightPlane;
        Duration duration = Duration.between(flightPlane.getDepartureTime().toLocalTime(), flightPlane.getArrivalTime().toLocalTime());
        this.duration = duration.toHours() + "h " + duration.toMinutesPart() + "m";
    }




}