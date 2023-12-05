package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.Baggage;
import com.example.demo.entity.FlightPlane;
import com.example.demo.entity.Seat;
import com.example.demo.entity.SeatDetail;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    FlightPlaneService flightPlaneService;
    @Autowired
    BaggageService baggageService;
    @Autowired
    SeatService seatService;
    @Autowired
    ReservationService reservationService;
    @Autowired
    TicketService ticketService;
    @GetMapping(value = {"", "/"})
    public String booking(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
//        if (session.getAttribute("bookingDetail") == null)
//            return "redirect:/booking";
        // test


//        seatService.initSeat(flightPlaneService.getFlightPlaneById(1), 100);
//        seatService.initSeat(flightPlaneService.getFlightPlaneById(2), 100);
//        seatService.initSeat(flightPlaneService.getFlightPlaneById(3), 100);

        session.setAttribute("flight1", 1);
        session.setAttribute("flight2", 3);
        FlightPlane flight1 = flightPlaneService.findById(1);
        FlightPlane flight2 = flightPlaneService.findById(3);



        BookingDetail form = new BookingDetail();
        List<PassengerDTO> passengerDTOS = new ArrayList<>();
        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setType("adult");
        passengerDTOS.add(passengerDTO);
        passengerDTO = new PassengerDTO();

        passengerDTO.setType("child");
        passengerDTOS.add(passengerDTO);
        passengerDTO = new PassengerDTO();
        passengerDTO.setType("infant");
        passengerDTOS.add(passengerDTO);
        form.setPassengerDTOS(passengerDTOS);

        model.addAttribute("form", form);
        model.addAttribute("flight1", new FlightDTO(flight1));
        model.addAttribute("flight2", new FlightDTO(flight2));
        return "booking";
    }

    @PostMapping(value = {"", "/"})
    public String postBookingDetail(HttpServletRequest req, BookingDetail form) {
        HttpSession session = req.getSession();
        session.setAttribute("bookingDetail", form);
        return "redirect:/booking/review";
    }


    @GetMapping("/review")
    public String review(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        if (session.getAttribute("bookingDetail") == null)
            return "redirect:/booking";

        BookingDetail bookingDetail = (BookingDetail) session.getAttribute("bookingDetail");
        FlightPlane flight1 = flightPlaneService.findById((int) session.getAttribute("flight1"));
        FlightPlane flight2 = flightPlaneService.findById((int) session.getAttribute("flight2"));
        List<Baggage> baggages = baggageService.getAllBaggages();

        int total = 0;
        total += flight1.getFlight().getFlightPrice() * bookingDetail.getPassengerDTOS().size();
        total += flight2.getFlight().getFlightPrice() * bookingDetail.getPassengerDTOS().size();

        if (!bookingDetail.isHasExtraBaggage1() && !bookingDetail.isHasExtraBaggage2()) {
            bookingDetail.setBaggageIds1(new ArrayList<>());
            bookingDetail.setBaggageIds2(new ArrayList<>());

            for (PassengerDTO p : bookingDetail.getPassengerDTOS()) {
                bookingDetail.getBaggageIds1().add(0);
                bookingDetail.getBaggageIds2().add(0);
            }
        }
        else {
            List<Baggage> baggageList1 = new ArrayList<>();
            int baggage1Sum = 0;
            int baggage2Sum = 0;
            for (int id : bookingDetail.getBaggageIds1()) {

                System.out.println(id);
                if (id != 0) {
                    Baggage baggage = baggageService.getById(id);
                    baggageList1.add(baggage);
                    baggage1Sum += baggage.getPrice();
                }
                else
                    baggageList1.add(null);

            }
            List<Baggage> baggageList2 = new ArrayList<>();
            for (int id : bookingDetail.getBaggageIds2()) {
                if (id != 0) {
                    Baggage baggage = baggageService.getById(id);
                    baggageList2.add(baggage);
                    baggage2Sum += baggage.getPrice();
                }
                else
                    baggageList2.add(null);
            }
            total += baggage1Sum + baggage2Sum;
            model.addAttribute("baggageList1", baggageList1);
            model.addAttribute("baggageList2", baggageList2);
            model.addAttribute("baggage1Sum", baggage1Sum);
            model.addAttribute("baggage2Sum", baggage2Sum);
        }

        if (!bookingDetail.isHasChosenSeat1() && !bookingDetail.isHasChosenSeat2()) {
            bookingDetail.setSeatDetailIds1(new ArrayList<>());
            bookingDetail.setSeatDetailIds2(new ArrayList<>());

            for (PassengerDTO p : bookingDetail.getPassengerDTOS()) {
                bookingDetail.getSeatDetailIds1().add(0);
                bookingDetail.getSeatDetailIds2().add(0);
            }
        }
        else {
            List<SeatDetail> chosenSeats1 = new ArrayList<>();
            for (int id : bookingDetail.getSeatDetailIds1()) {
                System.out.println(id);
                if (id != 0)
                    chosenSeats1.add(seatService.getSeatDetailById(id));
                else
                    chosenSeats1.add(null);

            }
            List<SeatDetail> chosenSeats2 = new ArrayList<>();
            for (int id : bookingDetail.getSeatDetailIds2()) {
                if (id != 0)
                    chosenSeats2.add(seatService.getSeatDetailById(id));
                else
                    chosenSeats2.add(null);

            }
            model.addAttribute("chosenSeats1", chosenSeats1);
            model.addAttribute("chosenSeats2", chosenSeats2);
        }

        List<SeatDetail> seatDetails1 = seatService.getSeatDetailsByFlightPlane(flight1);
        List<SeatDetail> seatDetails2 = seatService.getSeatDetailsByFlightPlane(flight2);

        model.addAttribute("total", total);
        model.addAttribute("seats1", new SeatContainer(seatDetails1));
        model.addAttribute("seats2", new SeatContainer(seatDetails2));
        model.addAttribute("bookingDetail", bookingDetail);
        model.addAttribute("flight1", new FlightDTO(flight1));
        model.addAttribute("flight2", new FlightDTO(flight2));
        model.addAttribute("baggages", baggages);
        return "booking-review";
    }

    @PostMapping("/review/baggage")
    public String postBaggage(HttpServletRequest req, BookingDetail newBookingDetail) {
        HttpSession session = req.getSession();
        if (session.getAttribute("bookingDetail") == null)
            return "redirect:/booking";

        BookingDetail bookingDetail = (BookingDetail) session.getAttribute("bookingDetail");

        for (int id : newBookingDetail.getBaggageIds1()) {
            if (id != 0) bookingDetail.setHasExtraBaggage1(true);
        }
        for (int id : newBookingDetail.getBaggageIds2()) {
            if (id != 0) bookingDetail.setHasExtraBaggage2(true);
        }

        bookingDetail.setBaggageIds1(newBookingDetail.getBaggageIds1());
        bookingDetail.setBaggageIds2(newBookingDetail.getBaggageIds2());
        session.setAttribute("bookingDetail", bookingDetail);

        return "redirect:/booking/review";
    }

    @PostMapping("/review/seat")
    public String postSeat(HttpServletRequest req, BookingDetail newBookingDetail) {
        HttpSession session = req.getSession();
        if (session.getAttribute("bookingDetail") == null)
            return "redirect:/booking";

        BookingDetail bookingDetail = (BookingDetail) session.getAttribute("bookingDetail");

        for (int id : newBookingDetail.getSeatDetailIds1()) {
            System.out.println(id);
            if (id != 0) bookingDetail.setHasChosenSeat1(true);
        }
        for (int id : newBookingDetail.getSeatDetailIds2()) {
            System.out.println(id);
            if (id != 0) bookingDetail.setHasChosenSeat2(true);
        }

        bookingDetail.setSeatDetailIds1(newBookingDetail.getSeatDetailIds1());
        bookingDetail.setSeatDetailIds2(newBookingDetail.getSeatDetailIds2());
        session.setAttribute("bookingDetail", bookingDetail);

        return "redirect:/booking/review";
    }

    @GetMapping("/complete")
    public String complete(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        if (session.getAttribute("bookingDetail") == null)
            return "redirect:/booking";

        BookingDetail bookingDetail = (BookingDetail) session.getAttribute("bookingDetail");



        return "booking-complete";
    }

}
