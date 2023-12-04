package com.example.demo.service;

import com.example.demo.entity.Flight;
import com.example.demo.entity.FlightPlane;
import com.example.demo.repository.FlightPlaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightPlaneService {
    @Autowired
    private FlightPlaneRepository flightPlaneRepository;

    public List<FlightPlane> getAllFlightPlanes() {
        return flightPlaneRepository.findAll();
    }

    public Optional<FlightPlane> getFlightPlaneById(int id){return this.flightPlaneRepository.findById(id);}


    public FlightPlane save(FlightPlane flightPlane){return this.flightPlaneRepository.save(flightPlane);}

    public void deleteFlightPlane(int id){
        this.flightPlaneRepository.deleteById(id);
    }

    public FlightPlane updateFlight(int id, FlightPlane flightPlane){
        Optional<FlightPlane> exist = this.flightPlaneRepository.findById(id);
        if (exist.isPresent()){
            flightPlane.setId(id);
            return this.flightPlaneRepository.save(flightPlane);
        }else{
            return null;
        }
    }
}
