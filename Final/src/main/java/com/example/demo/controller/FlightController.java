package com.example.demo.controller;

import com.example.demo.entity.Airport;
import com.example.demo.entity.Flight;
import com.example.demo.service.AirportService;
import com.example.demo.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

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
    public String saveFlight(@ModelAttribute("flight") Flight flight,
                             @RequestParam("departureAirport") int departureAirportId,
                             @RequestParam("arrivalAirport") int arrivalAirportId,
                             RedirectAttributes redirectAttributes) {

        if(departureAirportId == arrivalAirportId) {
            redirectAttributes.addFlashAttribute("errorMessage", "Same airport locations");
            return "redirect:/Admin/flight";
        }
        Optional<Airport> departureAirport = airportService.getAirportById(departureAirportId);
        Optional<Airport> arrivalAirport = airportService.getAirportById(arrivalAirportId);

        if (departureAirport.isPresent() && arrivalAirport.isPresent()){

            flight.setDepartureAirport(departureAirport.get());
            flight.setArrivalAirport(arrivalAirport.get());
        }
        flight.setStatus("Active");
        flightService.save(flight);
        return "redirect:/Admin/flight";
    }


    @PostMapping("/flight/update/{id}")
    public String updateFlight(@PathVariable("id") Integer flightId, Flight flight,
                               @RequestParam("departureAirport") int departureAirportId,
                               @RequestParam("arrivalAirport") int arrivalAirportId,
                               RedirectAttributes redirectAttributes){
        Flight originalFlight = flightService.getFlightById(flightId).orElse(null);

        if(departureAirportId == arrivalAirportId) {
            redirectAttributes.addFlashAttribute("errorMessage", "Same airport locations");
            return "redirect:/Admin/flight";
        }

        if(originalFlight == null) {
            return null;
        }

        originalFlight.setArrivalAirport(airportService.getAirportById(arrivalAirportId).orElse(null));
        originalFlight.setDepartureAirport(airportService.getAirportById(departureAirportId).orElse(null));
        originalFlight.setFlightAirline(flight.getFlightAirline());
        originalFlight.setFlightPrice(flight.getFlightPrice());

        flightService.save(originalFlight);
        return "redirect:/Admin/flight";

    }

    @PostMapping("/flight/delete/{id}")
    public String deleteFlight(@PathVariable("id") Integer flightId) {

        Flight flight = flightService.getFlightById(flightId).orElse(null);
        if(flight == null) {
            return "redirect:/Admin/flight";
        }
        flight.setStatus("Disabled");
        flightService.save(flight);
        return "redirect:/Admin/flight";

    }

}
