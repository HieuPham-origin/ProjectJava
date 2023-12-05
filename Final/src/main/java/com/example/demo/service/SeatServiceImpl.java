package com.example.demo.service;

import com.example.demo.entity.FlightPlane;
import com.example.demo.entity.Plane;
import com.example.demo.entity.Seat;
import com.example.demo.entity.SeatDetail;
import com.example.demo.repository.PlaneRepository;
import com.example.demo.repository.SeatRepository;
import com.example.demo.repository.SeatDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    SeatRepository seatRepository;
    @Autowired
    SeatDetailRepository seatDetailRepository;
    @Autowired
    PlaneRepository planeRepository;
    @Override
    public void initSeats(Plane plane) {
        String columns = "ABCDEF";
        for (int i = 1; i <= plane.getCapacity() / 6; i++) {
            for (int col = 0; col < 6; col ++) {
                String seatNumber = String.valueOf(i) + columns.charAt(col);
                Seat seat = new Seat();
                seat.setPlane(plane);
                seat.setSeatNumber(seatNumber);
                seatRepository.save(seat);
            }
        }
    }

    @Override
    public void initSeatDetails(FlightPlane flightPlane)
    {
        List<SeatDetail> seatDetails = new ArrayList<>();
        for (Seat seat : seatRepository.getAllByPlane(flightPlane.getPlane())) {
            SeatDetail seatDetail = new SeatDetail();
            seatDetail.setSeat(seat);
            seatDetail.setTaken(false);
            seatDetail.setFlightPlane(flightPlane);
            seatDetails.add(seatDetail);
        }
        seatDetailRepository.saveAll(seatDetails);
    }

    @Override
    public List<SeatDetail> getSeatDetailsByFlightPlane(FlightPlane flightPlane) {
        return seatDetailRepository.getByFlightPlane(flightPlane);
    }

    @Override
    public SeatDetail getSeatDetailById(int id) {
        return seatDetailRepository.findById(id).orElse(null);
    }
}
