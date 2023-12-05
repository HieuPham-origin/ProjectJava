package com.example.demo.service;

import com.example.demo.entity.Baggage;
import com.example.demo.repository.BaggageRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaggageServiceImpl implements BaggageService {
    @Autowired
    BaggageRepository baggageRepository;
    @Override
    public List<Baggage> getAllBaggages() {
        return baggageRepository.findAll();
    }

    @Override
    public Baggage getById(int id) {
        return baggageRepository.findById(id).orElse(null);
    }
}
