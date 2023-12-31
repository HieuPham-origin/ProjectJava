package com.example.demo.controller;

import com.example.demo.entity.Airport;
import com.example.demo.service.AirportService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    HttpSession httpSession;
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
    @PostMapping("/sendDataSessionForReturn")
    @ResponseBody
    public String sendDataSessionForReturn(@RequestParam("departureFlightId") String departureFlightId,
                                           @RequestParam("arrivalFlightId") String arrivalFlightId,
                                           @RequestParam("departureTicketClassId") int departureTicketClassId,
                                           @RequestParam("arrivalTicketClassId") int arrivalTicketClassId,
                                           @RequestParam("passengerNumInfo") String passengerNumInfo
                                           ){
        httpSession.setAttribute("isReturn",true);
        httpSession.setAttribute("flight1Id", Integer.parseInt(departureFlightId));
        httpSession.setAttribute("flight2Id", Integer.parseInt(arrivalFlightId));
        httpSession.setAttribute("ticketClass1Id",departureTicketClassId);
        httpSession.setAttribute("ticketClass2Id",arrivalTicketClassId);
        httpSession.setAttribute("passengerNumInfo",passengerNumInfo);
        return "success";
    }

    @PostMapping("/sendDataSessionForNotReturn")
    @ResponseBody
    public String sendDataSessionForNotReturn(@RequestParam("departureFlightId") String departureFlightId,
                                           @RequestParam("departureTicketClassId") int departureTicketClassId,
                                              @RequestParam("passengerNumInfo") String passengerNumInfo
    ){
        httpSession.setAttribute("isReturn",false);
        httpSession.setAttribute("flight1Id", Integer.parseInt(departureFlightId));
        httpSession.setAttribute("ticketClass1Id",departureTicketClassId);
        httpSession.setAttribute("passengerNumInfo",passengerNumInfo);
        return "success";
    }
}