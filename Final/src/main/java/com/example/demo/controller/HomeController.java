package com.example.demo.controller;

import com.example.demo.entity.Airport;
import com.example.demo.service.AirportService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.logging.Logger;

@Controller
public class HomeController {

    private final AirportService airportService;

    public HomeController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping("/")
    public String ShowHomePage(){
        return "redirect:/index";
    }
    @GetMapping("/index")
    public String index(Model model){
        List<Airport> airports = airportService.getAllAirports();
        model.addAttribute("airportList",airports);
        return "index";
    }
    @PostMapping("/index/search")
    @ResponseBody
    public List<Airport> indexSearchPlace(@RequestParam("newSearch") String newSearch) {
        return airportService.getAirportsByKeySearch(newSearch);
    }
}
