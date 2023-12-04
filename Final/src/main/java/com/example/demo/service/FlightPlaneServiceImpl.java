package com.example.demo.service;

import com.example.demo.entity.Flight;
import com.example.demo.entity.FlightPlane;
import com.example.demo.repository.FlightPlaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FlightPlaneServiceImpl implements FlightPlaneService{
    @Autowired
    private FlightPlaneRepository flightPlaneRepository;
    @Override
    public List<FlightPlane> findByFlightAndDepartureDayAndArrivalDay(Flight flightId, Date departureDay, Date arrivalDay) {
        return flightPlaneRepository.findByFlightAndDepartureDayAndArrivalDay(flightId,departureDay,arrivalDay);

    }

    @Override
    public List<FlightPlane> findByFlight(Flight flight) {
        return flightPlaneRepository.findByFlight(flight);
    }

    @Override
    public FlightPlane findById(int flightId) {
        Optional<FlightPlane> dbFlightPlane =  flightPlaneRepository.findById(flightId);
        return dbFlightPlane.orElse(null);
    }

    @Override
    public FlightPlane save(FlightPlane flightPlane) {
        return flightPlaneRepository.save(flightPlane);
    }

    @Override
    public List<FlightPlane> getAllFlightPlanes() {
        return flightPlaneRepository.findAll();
    }

    @Override
    public FlightPlane getFlightPlaneById(int id) {
        return flightPlaneRepository.findById(id).orElse(null);
    }
}
