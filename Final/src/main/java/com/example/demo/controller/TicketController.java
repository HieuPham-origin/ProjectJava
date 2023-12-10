package com.example.demo.controller;

import com.example.demo.entity.Reservation;
import com.example.demo.entity.Ticket;
import com.example.demo.service.ReservationService;
import com.example.demo.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Admin")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @GetMapping("/ticket")
    public String showReservations(Model model) {
        List<Ticket> tickets = ticketService.fetchAllTickets();
        model.addAttribute("ticketList", tickets);
        return "Admin/ticket-manager";
    }
}