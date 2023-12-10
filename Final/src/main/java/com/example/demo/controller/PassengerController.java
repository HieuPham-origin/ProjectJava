package com.example.demo.controller;

import com.example.demo.entity.Flight;
import com.example.demo.entity.Passenger;
import com.example.demo.entity.Plane;
import com.example.demo.entity.Ticket;
import com.example.demo.service.PassengerService;
import com.example.demo.service.PlaneService;
import com.example.demo.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Admin")
public class PassengerController {
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private TicketService ticketService;
    @GetMapping("/passenger")
    public String showPassengers(Model model){
        List<Passenger> passengers = passengerService.fetchAllPassenger();
        List<Ticket> tickets = new ArrayList<Ticket>();
        HashMap<Passenger, Ticket> passengerTicketMap = new HashMap<>();

        for (Passenger p : passengers) {
            Ticket ticket = ticketService.getTicketByPassenger(p);
            passengerTicketMap.put(p, ticket);
        }
        model.addAttribute("passengerList", passengerTicketMap);
        return "Admin/passenger-manager";
    }

}
