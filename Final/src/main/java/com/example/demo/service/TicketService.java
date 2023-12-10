package com.example.demo.service;

import com.example.demo.entity.Passenger;
import com.example.demo.entity.Ticket;

public interface TicketService {
    Ticket create(Ticket ticket);
    Ticket getTicketByPassenger(Passenger passenger);
}
