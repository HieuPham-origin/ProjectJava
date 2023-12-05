package com.example.demo.repository;

import com.example.demo.entity.FlightPlane;
import com.example.demo.entity.SeatDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatDetailRepository extends JpaRepository<SeatDetail, Integer> {
    List<SeatDetail> getByFlightPlane(FlightPlane flightPlane);
}
