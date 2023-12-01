package com.example.demo.service;

import com.example.demo.entity.Flight;

import java.util.List;
import java.util.Optional;

public interface FlightService {
    List<Flight> getAllFlights();
    Optional<Flight> getFlightById(int id);
    Flight save(Flight flight);
    void deleteFlight(int id);
    Flight updateFlight(int id, Flight flight);
}
