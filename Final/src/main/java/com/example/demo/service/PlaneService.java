package com.example.demo.service;

import com.example.demo.entity.Airport;
import com.example.demo.entity.Plane;

import java.util.List;
import java.util.Optional;

public interface PlaneService {
    List<Plane> getAllPlanes();
    public Optional<Plane> getPlaneById(int id);
    public Plane create(Plane plane);
    public void delete(int id);
    public Plane update(int id, Plane plane);
}
