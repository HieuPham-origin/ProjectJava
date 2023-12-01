package com.example.demo.service;

import com.example.demo.entity.Airport;

import java.util.List;
import java.util.Optional;

public interface AirportService {
    List<Airport> getAllAirports();
    Optional<Airport> getAirportById(int id);
    Airport save(Airport airport);
    void delete(int id);
    Airport update(int id, Airport airport);
}
