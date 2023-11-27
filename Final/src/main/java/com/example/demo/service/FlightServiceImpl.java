package com.example.demo.service;

import com.example.demo.entity.Flight;
import com.example.demo.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightServiceImpl implements FlightService{
    @Autowired
    private FlightRepository flightRepository;
    @Override
    public List<Flight> getAllFlight(){return this.flightRepository.findAll();}
    @Override
    public Optional<Flight> getFlightById(int id){return this.flightRepository.findById(id);}
    @Override
    public Flight addFlight(Flight flight){return this.flightRepository.save(flight);}
    @Override
    public void deleteFlight(int id){
        this.flightRepository.deleteById(id);
    }
    @Override
    public Flight updateFlight(int id, Flight flight){
        Optional<Flight> exist = this.flightRepository.findById(id);
        if (exist.isPresent()){
            flight.setFlightId(id);
            return this.flightRepository.save(flight);
        }else{
            return null;
        }
    }
}
