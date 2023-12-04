package com.example.demo.controller;

import com.example.demo.entity.Airport;
import com.example.demo.entity.Flight;
import com.example.demo.entity.FlightPlane;
import com.example.demo.service.FlightPlaneService;
import com.example.demo.service.FlightService;
import com.example.demo.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/Admin")
public class FlightPlaneController {
    @Autowired
    private FlightPlaneService flightPlaneService;
    @Autowired
    private FlightService flightService;
    @Autowired
    private PlaneController planeController;
    @GetMapping("/flightPlane")
    public String showFlightPlane(Model model){
        List<FlightPlane> flightPlanes = flightPlaneService.getAllFlightPlanes();
        model.addAttribute("flightPlaneList", flightPlanes);
        return "Admin/flight-manager";
    }

}
