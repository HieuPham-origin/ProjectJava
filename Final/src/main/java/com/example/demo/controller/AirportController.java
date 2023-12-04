package com.example.demo.controller;

import com.example.demo.entity.Airport;
import com.example.demo.entity.Flight;
import com.example.demo.service.AirportService;
import com.example.demo.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Admin")
public class AirportController {
    @Autowired
    private AirportService airportService;
    @Autowired
    private FlightService flightService;
    private Airport selectedAirport;
    @GetMapping("/airport")
    public String showAirport(Model model){
//        List<Airport> airports = airportService.getAllAirports();

        List<Airport> airports = airportService.getAllAirportsExceptStatus("Disabled");
        model.addAttribute("airportList", airports);
        model.addAttribute("prefix", "airport");
        model.addAttribute("selectedAirport", selectedAirport);
        return "Admin/airport-manager";
    }
    @PostMapping("/airport/save")
    public String saveAirport(Airport airportObj){
        airportObj.setStatus("Active");
        airportService.save(airportObj);
        return "redirect:/Admin/airport";
    }
    @PostMapping("/airport/delete/{id}")
    public String deleteAirport(@PathVariable("id") Integer airportId) {
//        flightService.deleteByAirportId(airportId,airportId);
//        airportService.delete(airportId);
        Airport airport = airportService.getAirportById(airportId).orElse(null);
        if(airport == null) {
            return "redirect:/Admin/airport";
        }
        airport.setStatus("Disabled");
        airportService.save(airport);
        return "redirect:/Admin/airport";

    }
    @PostMapping("/airport/update/{id}")
    public String updateAirport(@PathVariable("id") Integer airportId, Airport airport){
        Optional<Airport> exist = airportService.getAirportById(airportId);
        if(exist.isPresent()){
            airport.setAirportId(airportId);
            airportService.update(airportId, airport);
            selectedAirport = airport;
            return "redirect:/Admin/airport";
        } else {
            return null;
        }
    }
}
