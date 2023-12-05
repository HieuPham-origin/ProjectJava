package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.Reservation;

public interface ReservationService {
    Reservation create(Reservation reservation);
    Reservation findByAccount(Account account);
}
