package com.example.demo.service;

import com.example.demo.entity.Passenger;
import com.example.demo.entity.Ticket;

import java.util.List;

public interface TicketService {
    Ticket create(Ticket ticket);
    Ticket getTicketByPassenger(Passenger passenger);

    List<Ticket> fetchAllTickets();
}
