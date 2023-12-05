package com.example.demo.repository;

import com.example.demo.entity.Plane;
import com.example.demo.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
    List<Seat> getAllByPlane(Plane plane);
}
