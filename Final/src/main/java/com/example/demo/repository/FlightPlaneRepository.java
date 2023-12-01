package com.example.demo.repository;

import com.example.demo.entity.FlightPlane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightPlaneRepository extends JpaRepository<FlightPlane, Integer> {
}
