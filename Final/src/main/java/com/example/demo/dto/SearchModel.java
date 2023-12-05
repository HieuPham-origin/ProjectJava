package com.example.demo.dto;

import com.example.demo.entity.FlightPlane;
import com.example.demo.entity.TicketClass;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchModel {
    private List<TicketClass> ticketClassList;
    private List<FlightPlane> flightPlaneList;
    public SearchModel(List<TicketClass> ticketClassList, List<FlightPlane> flightPlaneList) {
        this.ticketClassList = ticketClassList;
        this.flightPlaneList = flightPlaneList;
    }
}
