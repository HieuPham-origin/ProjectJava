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

import java.net.http.HttpRequest;
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
                                           @RequestParam("passengerNumInfo") String passengerNumInfo,
                                           HttpServletRequest httpRequest
                                           ){
        httpRequest.getSession().setAttribute("isReturn",true);
        httpRequest.getSession().setAttribute("flight1Id",departureFlightId);
        httpRequest.getSession().setAttribute("flight2Id",arrivalFlightId);
        httpRequest.getSession().setAttribute("flight1TicketClassId",departureTicketClassId);
        httpRequest.getSession().setAttribute("flight2TicketClassId",arrivalTicketClassId);
        System.out.println(passengerNumInfo);
        return "success";
    }

    @PostMapping("/sendDataSessionForNotReturn")
    @ResponseBody
    public String sendDataSessionForNotReturn(@RequestParam("departureFlightId") String departureFlightId,
                                              @RequestParam("departureTicketClassId") int departureTicketClassId,
                                              @RequestParam("passengerNumInfo") String passengerNumInfo,
                                              HttpServletRequest httpRequest
    ){
        httpRequest.getSession().setAttribute("isReturn",false);
        httpRequest.getSession().setAttribute("flight1Id",departureFlightId);
        httpRequest.getSession().setAttribute("flight1TicketClassId",departureTicketClassId);
        System.out.println(passengerNumInfo);
        return "success";
    }
}