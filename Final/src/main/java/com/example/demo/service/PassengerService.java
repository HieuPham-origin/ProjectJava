package com.example.demo.service;

import com.example.demo.entity.Passenger;

import java.util.List;

public interface PassengerService {
    Passenger savePassenger(Passenger passenger);

    List<Passenger> fetchAllPassenger();

    Passenger updatePassenger(Passenger passenger, Integer passengerId);

    void deletePassenger(Integer passengerId);

    Passenger getPassengerByEmail(String email);

}
