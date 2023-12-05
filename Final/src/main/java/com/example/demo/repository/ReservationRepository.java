package com.example.demo.repository;

import com.example.demo.entity.Account;
import com.example.demo.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    boolean existsByCode(String code);
    Reservation findByAccount(Account account);
}
