package com.example.demo.service;

import com.example.demo.entity.Baggage;
import org.hibernate.mapping.Bag;

import java.util.List;

public interface BaggageService {
    List<Baggage> getAllBaggages();
    Baggage getById(int id);
}
