package com.example.demo.repository;

import com.example.demo.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {
    @Query("delete from Flight f where f.arrivalAirport=?1 or f.departureAirport=?2")
    public void deleteFlightByAirportId(int arId, int deId);
}
