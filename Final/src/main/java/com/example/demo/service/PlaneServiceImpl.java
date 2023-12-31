package com.example.demo.service;

import com.example.demo.entity.Plane;
import com.example.demo.repository.PlaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaneServiceImpl implements PlaneService{
    @Autowired
    private PlaneRepository planeRepository;
    @Override
    public List<Plane> getAllPlanes(String status){return this.planeRepository.findAllByStatusNot(status);}
    @Override
    public Optional<Plane> getPlaneById(int id){return this.planeRepository.findById(id);}
    @Override
    public Plane save(Plane plane){return this.planeRepository.save(plane);}
    @Override
    public void delete(int id){this.planeRepository.deleteById(id);}
    @Override
    public Plane update(int id, Plane plane){
        Optional<Plane> exist = this.planeRepository.findById(id);
        if(exist.isPresent()){
            plane.setPlaneId(id);
            plane.setStatus("Active");
            return this.planeRepository.save(plane);
        }else{
            return null;
        }
    }

}
