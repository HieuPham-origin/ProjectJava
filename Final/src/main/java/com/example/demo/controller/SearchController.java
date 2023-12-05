package com.example.demo.controller;

import com.example.demo.dto.SearchModel;
import com.example.demo.entity.Airport;
import com.example.demo.entity.Flight;
import com.example.demo.entity.FlightPlane;
import com.example.demo.entity.TicketClass;
import com.example.demo.service.AirportService;
import com.example.demo.service.FlightPlaneService;
import com.example.demo.service.FlightService;
import com.example.demo.service.TicketClassService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
@RequestMapping("/search")
public class SearchController {
    private final AirportService airportService;
    private final FlightService flightService;
    private final FlightPlaneService flightPlaneService;
    private final TicketClassService ticketClassService;

    public SearchController(AirportService airportService, FlightService flightService, FlightPlaneService flightPlaneService, TicketClassService ticketClassService) {
        this.airportService = airportService;
        this.flightService = flightService;
        this.flightPlaneService = flightPlaneService;
        this.ticketClassService = ticketClassService;
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
        int sumPax = sumOfNumbers(passenger);
        model.addAttribute("sumPax", sumPax);
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
    public SearchModel searchTicket(@RequestParam("dateDeparture") String dateDeparture,
                                          @RequestParam("departureText") String departureText,
                                          @RequestParam("destinationText") String destinationText,
                                          @RequestParam(value = "sortByDuration", required = false) String sortByDuration,
                                          @RequestParam(value = "sortByPrice", required = false) String sortByPrice,
                                          @RequestParam(value = "inputFilterAirline", required = false) String inputFilterAirline,
                                          @RequestParam(value = "inputFilterDeparture", required = false) String inputFilterDeparture,
                                          @RequestParam(value = "inputFilterArrival", required = false) String inputFilterArrival){

        //Get list flight by departure and destination
        String departureAirportCode =  departureText.split(" , ")[0];
        String destinationAirportCode =  destinationText.split(" , ")[0];
        Airport airportDeparture = airportService.getAirportByAirPortCode(departureAirportCode);
        Airport airportDestination = airportService.getAirportByAirPortCode(destinationAirportCode);

        List<Flight> flights = flightService.getFlightsByDepartureAndDestination(airportDeparture.getAirportId(), airportDestination.getAirportId());
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<FlightPlane> result = new ArrayList<>();
        System.out.println("1");
        for (Flight flight: flights) {
            List<FlightPlane> flightPlaneList = flightPlaneService.findByFlight(flight);
            for (FlightPlane flightPlane: flightPlaneList){
                Date date = convertToLocalDateToDate(flightPlane.getDepartureDay());
                String indexDateDeparture = outputFormat.format(date);
                if (dateDeparture.equals(indexDateDeparture)){
                    result.add(flightPlane);
                }
            }
        }
        System.out.println("1");

        // get list filter airline
        List<FlightPlane> resultFilterAirline = new ArrayList<>();
        if (inputFilterAirline!=null){
            if (inputFilterAirline.contains("vna") && !inputFilterAirline.contains("vietjet")){
                for (FlightPlane flightPlane:  result) {
                    if (flightPlane.getFlight().getFlightAirline().toLowerCase().contains("vietnam airlines")){
                        resultFilterAirline.add(flightPlane);
                    };
                }
            } else if (inputFilterAirline.contains("vietjet") && !inputFilterAirline.contains("vna")) {
                for (FlightPlane flightPlane:  result) {
                    if (flightPlane.getFlight().getFlightAirline().toLowerCase().contains("vietjet air")){
                        resultFilterAirline.add(flightPlane);
                    };
                }
            } else {
                resultFilterAirline = result;
            }
        } else{
            resultFilterAirline = result;
        }
        // get list filter departure time
        List<FlightPlane> resultFilterDeparture = result;
        if (inputFilterDeparture != null) {
            if (inputFilterDeparture.contains("day") && !inputFilterDeparture.contains("night")) {
                resultFilterDeparture = getByFilterDeparture(result, Time.valueOf("00:00:00"), Time.valueOf("12:00:00"));
            } else if (inputFilterDeparture.contains("night") && !inputFilterDeparture.contains("day")) {
                resultFilterDeparture = getByFilterDeparture(result, Time.valueOf("12:00:00"), Time.valueOf("23:59:59"));
            }
        }
        // get list filter arrival time
        List<FlightPlane> resultFilterArrival = result;
        if (inputFilterArrival != null) {
            if (inputFilterArrival.contains("day") && !inputFilterArrival.contains("night")) {
                resultFilterArrival = getByFilterArrival(result, Time.valueOf("00:00:00"), Time.valueOf("12:00:00"));
            } else if (inputFilterArrival.contains("night") && !inputFilterArrival.contains("day")) {
                resultFilterArrival = getByFilterArrival(result, Time.valueOf("12:00:00"), Time.valueOf("23:59:59"));
            }
        }

        List<FlightPlane> intersectionList = new ArrayList<>(resultFilterAirline);
        intersectionList.retainAll(resultFilterDeparture);
        intersectionList.retainAll(resultFilterArrival);

        List<TicketClass> ticketClassList = this.ticketClassService.findAll();

        if(sortByDuration != null && sortByPrice != null) {
            return new SearchModel(ticketClassList, sortFlightByPriceAndDuration(intersectionList, sortByPrice, sortByDuration));
        }

        if (sortByDuration!=null){
           return new SearchModel(ticketClassList, sortFlightsByDuration(intersectionList, sortByDuration));

        }
        if (sortByPrice!=null){
            return new SearchModel(ticketClassList, sortFlightByPrice(intersectionList, sortByPrice));
        }
        return new SearchModel(ticketClassList, intersectionList);
        //return intersectionList;
    }
    @PostMapping("/bookingFlight")
    @ResponseBody
    public FlightPlane bookingFlight(@RequestParam("flightPlaneId") String flightPlaneId){
        return  flightPlaneService.findById(Integer.parseInt(flightPlaneId));
    }

    @PostMapping("/getTicketClass")
    @ResponseBody
    public List<TicketClass> ticketClassList(){
        return this.ticketClassService.findAll();
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

    public static List<FlightPlane> sortFlightByPrice(List<FlightPlane> originalFlightPlanes, String orderBy) {
        List<FlightPlane> sortedFlightPlanes = new ArrayList<>(originalFlightPlanes);
        Comparator<FlightPlane> durationComparator = Comparator.comparingInt(FlightPlane::getPriceForSort);
        Collections.sort(sortedFlightPlanes, durationComparator);
        if (orderBy.equals("desc")){
            Collections.reverse(sortedFlightPlanes);
        }
        return sortedFlightPlanes;
    }

    public static List<FlightPlane> sortFlightByPriceAndDuration(List<FlightPlane> flightPlanes, String priceSortOrder, String durationSortOrder) {
        List<FlightPlane> sortedFlightPlanes = new ArrayList<>(flightPlanes);

        // Define the custom comparator based on the sort orders, with duration as the primary sorting criterion
        Comparator<FlightPlane> customComparator = Comparator.comparingLong(FlightPlane::getDuration)
                .thenComparing(Comparator.comparingInt(FlightPlane::getPriceForSort));

        // Check the priceSortOrder input and adjust the sorting order accordingly
        if (priceSortOrder.equalsIgnoreCase("desc")) {
            customComparator = customComparator.thenComparing(Comparator.comparingInt(FlightPlane::getPriceForSort).reversed());
        }

        // Check the durationSortOrder input and adjust the sorting order accordingly
        if (durationSortOrder.equalsIgnoreCase("desc")) {
            customComparator = customComparator.reversed();
        }

        // Sort the flightPlanes list using the custom comparator
        sortedFlightPlanes.sort(customComparator);

        return sortedFlightPlanes;
    }



//    public static List<FlightPlane> sortFlightByPrice(List<FlightPlane> originalFlightPlanes, String orderBy) {
//        List<FlightPlane> sortedFlightPlanes = new ArrayLis<>(originalFlightPlanes);
//
//        // Define the price comparator
//        Comparator<FlightPlane> priceComparator = Comparator.comparingInt(FlightPlane::getPriceForSort);
//
//        // Sort the flightPlanes list based on the price
//        sortedFlightPlanes.sort(priceComparator);
//
//        // Check the orderBy input and adjust the sorting order accordingly
//        if (orderBy.equalsIgnoreCase("desc")) {
//            Collections.reverse(sortedFlightPlanes);
//        }
//
//        return sortedFlightPlanes;
//    }

    public static List<FlightPlane> getByFilterDeparture(List<FlightPlane> flightPlaneList, Time start, Time end) {
        List<FlightPlane> result = new ArrayList<>();
        for (FlightPlane flightPlane : flightPlaneList) {
            Time timeDeparture = Time.valueOf(flightPlane.getDepartureTime());

            if (timeDeparture.after(start) && timeDeparture.before(end)) {
                result.add(flightPlane);
            }
        }
        return result;
    }

    public static List<FlightPlane> getByFilterArrival(List<FlightPlane> flightPlaneList, Time start, Time end) {
        List<FlightPlane> result = new ArrayList<>();
        for (FlightPlane flightPlane : flightPlaneList) {

            Time timeArrival = Time.valueOf(flightPlane.getArrivalTime());
            if (timeArrival.after(start) && timeArrival.before(end)) {

                result.add(flightPlane);
            }
        }
        return result;
    }
    public static int sumOfNumbers(String input) {
        List<Integer> numbers = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String numberStr = matcher.group(); // Get the matched number as a string
            int number = Integer.parseInt(numberStr); // Convert the string to an integer
            numbers.add(number);
        }
        return numbers.stream().mapToInt(Integer::intValue).sum();
    }
    private static Date convertToLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
