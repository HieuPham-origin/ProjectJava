package com.example.demo.controller;

import com.example.demo.entity.Airport;
import com.example.demo.entity.Flight;
import com.example.demo.entity.FlightPlane;
import com.example.demo.service.AirportService;
import com.example.demo.service.FlightPlaneService;
import com.example.demo.service.FlightService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Controller
@RequestMapping("/search")
public class SearchController {
    private final AirportService airportService;
    private final FlightService flightService;
    private final FlightPlaneService flightPlaneService;

    public SearchController(AirportService airportService, FlightService flightService, FlightPlaneService flightPlaneService) {
        this.airportService = airportService;
        this.flightService = flightService;
        this.flightPlaneService = flightPlaneService;
    }

    @PostMapping("")
    public String showSearchPage(@RequestParam("departure") String departure,
                                 @RequestParam("destination") String destination,
                                 @RequestParam("dates") String dates,
                                 @RequestParam("passenger") String passenger,
                                 @RequestParam(value = "checkReturn",required=false) String checkReturn,
                                 Model model) throws ParseException {
        if (checkReturn!=null){
            model.addAttribute("checkReturn","true");
        } else{
            model.addAttribute("checkReturn","false");
        }
        //Render in search info
        model.addAttribute("departure",departure);
        model.addAttribute("destination",destination);
        model.addAttribute("dates",dates);
        model.addAttribute("passenger",passenger);
        model.addAttribute("departureCity", departure.split(" , ")[1]);
        model.addAttribute("destinationCity", destination.split(" , ")[1]);

        if (checkReturn!=null){
            if (checkReturn.equals("on")){
                String[] dateReturns = dates.split(" - ");
                String dateDepartureStr = dateReturns[0];
                String dateDestinationStr = dateReturns[1];

                SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yy");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

                Date dateDeparture = inputFormat.parse(dateDepartureStr);
                String formattedDateDeparture = outputFormat.format(dateDeparture);

                Date dateDestination = inputFormat.parse(dateDestinationStr);
                String formattedDateDest = outputFormat.format(dateDestination);
                //Render in left
                model.addAttribute("dateDeparture",formattedDateDeparture);
                model.addAttribute("dateDestination",formattedDateDest);
            }
        } else{
            String[] dateReturns = dates.split(" - ");
            String dateDepartureStr = dateReturns[0];

            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yy");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date dateDeparture = inputFormat.parse(dateDepartureStr);
            String formattedDateDeparture = outputFormat.format(dateDeparture);
            //Render in left
            model.addAttribute("dateDeparture",formattedDateDeparture);
            model.addAttribute("dateDestination","");
        }
        return "flights";
    }

    @PostMapping("/api")
    @ResponseBody
    public List<Airport> indexSearchPlace(@RequestParam("newSearch") String newSearch) {
        return airportService.getAirportsByKeySearch(newSearch);
    }

    @PostMapping("/searchTicket")
    @ResponseBody
    public List<FlightPlane> searchTicket(@RequestParam("dateDeparture") String dateDeparture,
                                          @RequestParam("departureText") String departureText,
                                          @RequestParam("destinationText") String destinationText,
                                          @RequestParam(value = "sortByDuration", required = false) String sortByDuration){

        //Get list flight by departure and destination
        String departureAirportCode =  departureText.split(" , ")[0];
        String destinationAirportCode =  destinationText.split(" , ")[0];
        Airport airportDeparture = airportService.getAirportByAirPortCode(departureAirportCode);
        Airport airportDestination = airportService.getAirportByAirPortCode(destinationAirportCode);
        List<Flight> flights = flightService.getFlightsByDepartureAndDestination(airportDeparture.getAirportId(), airportDestination.getAirportId());

        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<FlightPlane> result = new ArrayList<>();
        for (Flight flight: flights) {
            List<FlightPlane> flightPlaneList = flightPlaneService.findByFlight(flight);
            for (FlightPlane flightPlane: flightPlaneList){
                String indexDateDeparture = outputFormat.format(flightPlane.getDepartureDay());
                if (dateDeparture.equals(indexDateDeparture)){
                    result.add(flightPlane);
                }
            }
        }
        if (sortByDuration!=null){
            return sortFlightsByDuration(result, sortByDuration);
        }
        return result;
    }
    @PostMapping("/bookingFlight")
    @ResponseBody
    public FlightPlane bookingFlight(@RequestParam("flightPlaneId") String flightPlaneId){
        return  flightPlaneService.findById(Integer.parseInt(flightPlaneId));
    }

    public static List<FlightPlane> sortFlightsByDuration(List<FlightPlane> originalFlightPlanes, String orderBy) {
        List<FlightPlane> sortedFlightPlanes = new ArrayList<>(originalFlightPlanes);
        Comparator<FlightPlane> durationComparator = Comparator.comparingLong(FlightPlane::getDuration);
        Collections.sort(sortedFlightPlanes, durationComparator);
        if (orderBy.equals("desc")){
            Collections.reverse(sortedFlightPlanes);
        }
        return sortedFlightPlanes;
    }
}
