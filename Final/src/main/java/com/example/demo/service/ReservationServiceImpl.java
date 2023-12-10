package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.Reservation;
import com.example.demo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    ReservationRepository reservationRepository;
    @Override
    public Reservation create(Reservation reservation) {
        String code = generateCode().toUpperCase();
        while (reservationRepository.existsByCode(code)) {
            code = generateCode().toUpperCase();
        }
        reservation.setCode(code);
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> findByAccount(Account account) {
        return this.reservationRepository.findByAccount(account);
    }

    private String generateCode() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public Reservation findById(int reservationId){
        return reservationRepository.findById(reservationId).orElse(null);
    }

    @Override
    public List<Reservation> fetchAllReservation() {
        return reservationRepository.findAll();
    }
}
