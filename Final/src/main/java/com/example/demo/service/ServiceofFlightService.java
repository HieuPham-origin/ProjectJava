package com.example.demo.service;

import com.example.demo.entity.Service;

import java.util.List;

public interface ServiceofFlightService {
    List<Service> getAllServices();
    Service getById(int id);
}
