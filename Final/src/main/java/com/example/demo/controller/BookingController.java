package com.example.demo.controller;

import com.example.demo.dto.BookingDetail;
import com.example.demo.dto.ContactDetail;
import com.example.demo.dto.FlightDTO;
import com.example.demo.dto.PassengerDTO;
import com.example.demo.entity.Baggage;
import com.example.demo.entity.FlightPlane;
import com.example.demo.service.BaggageService;
import com.example.demo.service.FlightPlaneService;
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
    @GetMapping(value = {"", "/"})
    public String booking(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
//        if (session.getAttribute("bookingDetail") == null)
//            return "redirect:/booking";
        // test
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
            for (int id : bookingDetail.getBaggageIds1()) {
                System.out.println(id);
                if (id != 0)
                    baggageList1.add(baggageService.getById(id));
                else
                    baggageList1.add(null);

            }
            baggageList1.forEach(System.out::println);

            List<Baggage> baggageList2 = new ArrayList<>();
            for (int id : bookingDetail.getBaggageIds2()) {
                if (id != 0)
                    baggageList2.add(baggageService.getById(id));
                else
                    baggageList2.add(null);
            }
            model.addAttribute("baggageList1", baggageList1);
            model.addAttribute("baggageList2", baggageList2);
        }


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

    @GetMapping("/complete")
    public String complete() {
        return "booking-complete";
    }
}
