package com.example.demo.controller;

import com.example.demo.entity.Flight;
import com.example.demo.entity.Passenger;
import com.example.demo.entity.Plane;
import com.example.demo.service.PassengerService;
import com.example.demo.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Admin")
public class PassengerController {
    @Autowired
    private PassengerService passengerService;
    @GetMapping("/passenger")
    public String showPassengers(Model model){
        List<Passenger> passengers = passengerService.fetchAllPassenger();
        model.addAttribute("passengerList", passengers);
        return "Admin/passenger-manager";
    }

}
