package com.example.demo.repository;

import com.example.demo.entity.Flight;
import com.example.demo.entity.FlightPlane;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface FlightPlaneRepository extends JpaRepository<FlightPlane, Integer> {
    List<FlightPlane> findByFlight(Flight flight);
    List<FlightPlane> findByFlightAndDepartureDayAndArrivalDay(Flight flightId, Date departureDay, Date arrivalDay);
}
