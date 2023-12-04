package com.example.demo.repository;

import com.example.demo.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Integer> {
<<<<<<< HEAD
    List<Airport> findByAirportCode(String airportCode);
=======
    List<Airport> findAllByStatusNot(String status);
>>>>>>> 6ad1c308d5eedc874c064631d0094ee765482c1a
}
