package com.example.demo.service;

import com.example.demo.entity.TicketClass;

import java.util.List;

public interface TicketClassService {
    List<TicketClass> findAll();
    TicketClass getById(int id);
}
