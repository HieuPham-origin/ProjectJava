package com.example.demo.repository;

import com.example.demo.entity.Plane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaneRepository extends JpaRepository<Plane, Integer> {
    List<Plane> findAllByStatusNot(String status);
}
