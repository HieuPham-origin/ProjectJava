package com.example.demo.controller;

import com.example.demo.entity.Airport;
import com.example.demo.entity.Flight;
import com.example.demo.service.AirportService;
import com.example.demo.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/Admin")
public class FlightController {
    @Autowired
    private FlightService flightService;
    @Autowired
    private AirportService airportService;
    @GetMapping("/flight")
    public String showAirport(Model model){
        List<Flight> flights = flightService.getAllFlights();
        List<Airport> airports = airportService.getAllAirports();
        model.addAttribute("flightList", flights);
        model.addAttribute("prefix", "flight");
        model.addAttribute("airportList", airports);
        return "Admin/flight-manager";
    }
    @PostMapping("/flight/save")
    public String saveFlight(Flight flight){
        flightService.save(flight);
        return "redirect:/Admin/flight";
    }
}
