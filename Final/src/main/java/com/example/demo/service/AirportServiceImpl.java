package com.example.demo.service;

import com.example.demo.entity.Airport;
import com.example.demo.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportServiceImpl implements AirportService{
    @Autowired
    private AirportRepository airportRepository;
    @Override
    public List<Airport> getAllAirports(){return this.airportRepository.findAll();}
    @Override
    public Optional<Airport> getAirportById(int id){return this.airportRepository.findById(id);}
    @Override
    public Airport create(Airport airport){return this.airportRepository.save(airport);}
    @Override
    public void delete(int id){this.airportRepository.deleteById(id);}
    @Override
    public Airport update(int id, Airport airport){
        Optional<Airport> exist = this.airportRepository.findById(id);
        if(exist.isPresent()){
            airport.setAirportId(id);
            return this.airportRepository.save(airport);
        }else{
            return null;
        }
    }
}
