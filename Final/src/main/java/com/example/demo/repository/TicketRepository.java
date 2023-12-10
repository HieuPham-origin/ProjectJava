package com.example.demo.repository;

import com.example.demo.entity.Passenger;
import com.example.demo.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    Ticket findByPassenger(Passenger passenger);
}
