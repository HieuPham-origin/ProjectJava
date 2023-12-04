package com.example.demo.service;

import com.example.demo.entity.Passenger;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PassengerServiceImpl implements PassengerService{
    private final PassengerRepository passengerRepository;
    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }
    @Override
    public Passenger savePassenger(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    @Override
    public List<Passenger> fetchAllPassenger() {
        return passengerRepository.findAll();
    }

    @Override
    public Passenger updatePassenger(Passenger passenger, Integer passengerId) {
        return null;
    }

    @Override
    public Passenger getPassengerByEmail(String email) {
        return passengerRepository.findByEmail(email);
    }


    @Override
    public void deletePassenger(Integer passengerId) {
        passengerRepository.deleteById(passengerId);
    }
}
