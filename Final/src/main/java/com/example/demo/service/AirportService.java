package com.example.demo.service;

import com.example.demo.entity.Airport;

import java.util.List;
import java.util.Optional;

public interface AirportService {
    List<Airport> getAllAirports();
    public Optional<Airport> getAirportById(int id);
    public Airport create(Airport airport);
    public void delete(int id);
    public Airport update(int id, Airport airport);
    List<Airport> getAirportsByKeySearch(String keySearch);
}
