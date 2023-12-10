package com.example.demo.controller;

import com.example.demo.entity.Airport;
import com.example.demo.entity.Reservation;
import com.example.demo.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Admin")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservation")
    public String showReservations(Model model) {
        List<Reservation> reservations = reservationService.fetchAllReservation();
        model.addAttribute("reservationList", reservations);
        return "Admin/reservation-manager";
    }
}