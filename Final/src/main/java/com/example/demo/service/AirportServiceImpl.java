package com.example.demo.service;

import com.example.demo.entity.Airport;
import com.example.demo.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Airport save(Airport airport){return this.airportRepository.save(airport);}
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

    @Override
    public List<Airport> getAirportsByKeySearch(String keySearch) {
        List<Airport> airports = getAllAirports();
        List<Airport> result = new ArrayList<>();
        for (Airport airport: airports) {
            if (airport.getAirportCode().toLowerCase().contains(keySearch.toLowerCase())
                    || airport.getAirportName().toLowerCase().contains(keySearch.toLowerCase())
                    || airport.getCountry().toLowerCase().contains(keySearch.toLowerCase())
                    || airport.getCity().toLowerCase().contains(keySearch.toLowerCase())){
                result.add(airport);
            }
        }
        return result;
    }

    @Override
    public List<Airport> getAllAirportsExceptStatus(String status) {
        return airportRepository.findAllByStatusNot(status);
    }
}
