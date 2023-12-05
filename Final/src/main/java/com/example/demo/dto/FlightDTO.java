package com.example.demo.dto;

import com.example.demo.entity.FlightPlane;
import com.example.demo.entity.TicketClass;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@Getter @Setter
public class FlightDTO {
    private FlightPlane flightPlane;
    private String duration;
    private int priceTicket;
    private int ticketClassId;

    public FlightDTO(FlightPlane flightPlane, int ticketClassId) {
        this.flightPlane = flightPlane;
        this.ticketClassId = ticketClassId;
//        Duration duration = Duration.between(flightPlane.getDepartureTime().toLocalTime(), flightPlane.getArrivalTime().toLocalTime());
        Duration duration = Duration.between(flightPlane.getDepartureTime(), flightPlane.getArrivalTime());
        this.duration = duration.toHours() + "h " + duration.toMinutesPart() + "m";
        this.priceTicket = this.ticketClassId * this.flightPlane.getFlight().getFlightPrice();
    }
}
