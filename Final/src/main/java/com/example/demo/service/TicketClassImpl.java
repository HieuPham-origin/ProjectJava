package com.example.demo.service;

import com.example.demo.entity.TicketClass;
import com.example.demo.repository.TicketClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketClassImpl implements TicketClassService{
    @Autowired
    TicketClassRepository ticketClassRepository;
    @Override
    public List<TicketClass> findAll() {
        return ticketClassRepository.findAll();
    }
}
