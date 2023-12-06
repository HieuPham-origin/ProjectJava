package com.example.demo.service;

import com.example.demo.entity.Service;
import com.example.demo.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
@org.springframework.stereotype.Service
public class ServiceofFlightServiceImpl implements ServiceofFlightService{
    @Autowired
    private ServiceRepository serviceRepository;
    @Override
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    @Override
    public Service getById(int id) {
        return serviceRepository.findById(id).orElse(null);
    }
}
