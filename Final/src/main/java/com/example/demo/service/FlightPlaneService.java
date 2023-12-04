package com.example.demo.service;

import com.example.demo.entity.Flight;
import com.example.demo.entity.FlightPlane;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface FlightPlaneService {
    List<FlightPlane> findByFlightAndDepartureDayAndArrivalDay(Flight flightId, Date departureDay, Date arrivalDay);
    List<FlightPlane> findByFlight(Flight flight);
    FlightPlane findById(int flightId);

}
