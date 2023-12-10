package com.example.demo.service;

import com.example.demo.entity.Passenger;
import com.example.demo.entity.Ticket;
import com.example.demo.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService{
    @Autowired
    TicketRepository ticketRepository;
    @Override
    public Ticket create(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
    @Override
    public Ticket getTicketByPassenger(Passenger passenger){
        return this.ticketRepository.findByPassenger(passenger);
    }
}
