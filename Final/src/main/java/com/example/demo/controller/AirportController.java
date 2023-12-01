package com.example.demo.controller;

import com.example.demo.entity.Airport;
import com.example.demo.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/Admin")
public class AirportController {
    @Autowired
    private AirportService airportService;
    @GetMapping("/airport")
    public String showAirport(Model model){
        List<Airport> airports = airportService.getAllAirports();
        model.addAttribute("airportList", airports);
        model.addAttribute("prefix", "airport");
        return "Admin/airport-manager";
    }
    @PostMapping("/airport/save")
    public String saveAirport(Airport airportObj){
        airportService.save(airportObj);
        return "redirect:/Admin/airport";
    }
}
