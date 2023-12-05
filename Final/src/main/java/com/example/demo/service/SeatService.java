package com.example.demo.service;

import com.example.demo.entity.FlightPlane;
import com.example.demo.entity.Plane;
import com.example.demo.entity.SeatDetail;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SeatService {
    void initSeats(Plane plane);
    void initSeatDetails(FlightPlane flightPlane);
    List<SeatDetail> getSeatDetailsByFlightPlane(FlightPlane flightPlane);
    SeatDetail getSeatDetailById(int id);
}
