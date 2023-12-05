package com.example.demo.service;

import com.example.demo.entity.TicketClass;
import com.example.demo.repository.TicketClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketClassServiceImpl implements TicketClassService{
    @Autowired
    TicketClassRepository ticketClassRepository;
    @Override
    public List<TicketClass> findAll() {
        return ticketClassRepository.findAll();
    }

    @Override
    public TicketClass getById(int id) {
        return ticketClassRepository.findById(id).orElse(null);
    }
}
