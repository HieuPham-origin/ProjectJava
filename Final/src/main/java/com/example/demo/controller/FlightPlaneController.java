package com.example.demo.controller;

import com.example.demo.entity.Airport;
import com.example.demo.entity.Flight;
import com.example.demo.entity.FlightPlane;
import com.example.demo.entity.Plane;
import com.example.demo.service.FlightPlaneService;
import com.example.demo.service.FlightService;
import com.example.demo.service.PassengerService;
import com.example.demo.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Admin")
public class FlightPlaneController {
    @Autowired
    private FlightPlaneService flightPlaneService;
    @Autowired
    private FlightService flightService;
    @Autowired
    private PlaneService planeService;
    @GetMapping("/flightPlane")
    public String showFlightPlane(Model model){
        List<FlightPlane> flightPlanes = flightPlaneService.getAllFlightPlanes();
        List<Flight> flights = flightService.getAllFlights();
        List<Plane> planes = planeService.getAllPlanes("Disabled");
        model.addAttribute("flightPlaneList", flightPlanes);
        model.addAttribute("flightList", flights);
        model.addAttribute("planeList", planes);
        return "Admin/flight-plane-manager";
    }

    @PostMapping("/flightPlane/save")
    public String savePlane(@RequestParam("flight") int flightId,
                            @RequestParam("plane") int planeId,
                            @RequestParam("departureDay") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate departureDay,
                            @RequestParam("departureTime") @DateTimeFormat(pattern = "HH:mm") LocalTime departureTime,
                            @RequestParam("arrivalDay") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate arrivalDay,
                            @RequestParam("arrivalTime") @DateTimeFormat(pattern = "HH:mm") LocalTime arrivalTime){
        Flight flight = flightService.getFlightById(flightId).orElse(null);
        Plane plane = planeService.getPlaneById(planeId).orElse(null);

        if (flight == null || plane == null) {
            return null; // Handle the case when the flight or plane is not found
        }

        FlightPlane flightPlane = new FlightPlane();
        flightPlane.setFlight(flight);
        flightPlane.setPlane(plane);
        flightPlane.setDepartureDay(departureDay);
        flightPlane.setDepartureTime(departureTime);
        flightPlane.setArrivalDay(arrivalDay);
        flightPlane.setArrivalTime(arrivalTime);

        flightPlaneService.save(flightPlane);
        return "redirect:/Admin/flightPlane";
    }

    @PostMapping("/flightPlane/update/{id}")
    public String updatePlane(@PathVariable("id") Integer flightPlaneId,
                              FlightPlane updatedFlightPlane,
                              @RequestParam("flight") int flightId,
                              @RequestParam("plane") int planeId) {

        FlightPlane originalFlightPlane = flightPlaneService.getFlightPlaneById(flightPlaneId);

        if (originalFlightPlane == null) {
            return null;
        }

        originalFlightPlane.setFlight(flightService.getFlightById(flightId).orElse(null));
        originalFlightPlane.setPlane(planeService.getPlaneById(planeId).orElse(null));
        originalFlightPlane.setDepartureDay(updatedFlightPlane.getDepartureDay());
        originalFlightPlane.setDepartureTime(updatedFlightPlane.getDepartureTime());
        originalFlightPlane.setArrivalDay(updatedFlightPlane.getArrivalDay());
        originalFlightPlane.setArrivalTime(updatedFlightPlane.getArrivalTime());

        flightPlaneService.save(originalFlightPlane);
        return "redirect:/Admin/flightPlane";
    }


}