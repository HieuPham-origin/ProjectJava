package com.example.demo.service;

import com.example.demo.entity.Reservation;
import com.example.demo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    ReservationRepository reservationRepository;
    @Override
    public Reservation create(Reservation reservation) {
        return reservationRepository.save(reservation);
    }
}
