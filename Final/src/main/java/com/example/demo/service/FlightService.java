package com.example.demo.service;

import com.example.demo.entity.Flight;

import java.util.List;
import java.util.Optional;

public interface FlightService {
    List<Flight> getAllFlight();
    Optional<Flight> getFlightById(int id);
    Flight addFlight(Flight flight);
    void deleteFlight(int id);
    Flight updateFlight(int id, Flight flight);
}
