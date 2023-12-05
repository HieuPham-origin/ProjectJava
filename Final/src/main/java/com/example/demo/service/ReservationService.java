package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.Reservation;

import java.util.List;

public interface ReservationService {
    Reservation create(Reservation reservation);
    List<Reservation> findByAccount(Account account);
}
