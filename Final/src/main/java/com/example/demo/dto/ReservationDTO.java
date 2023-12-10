package com.example.demo.dto;

import com.example.demo.entity.FlightPlane;
import com.example.demo.entity.Passenger;
import com.example.demo.entity.Reservation;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ReservationDTO {
    private int numberOfPassengers;
    private Reservation reservation;
    private FlightPlane flightPlane1;
    private FlightPlane flightPlane2;
    private boolean isTwoWay;
    private String duration1;
    private String duration2;
    private List<ReservationPassengerDTO> passengers = new ArrayList<>();


    public ReservationDTO(Reservation reservation) {
        isTwoWay = reservation.getIsTwoway();
        if (isTwoWay) {
            numberOfPassengers = reservation.getTickets().size()/2;
            flightPlane1 = reservation.getTickets().get(0).getSeatDetail().getFlightPlane();
            flightPlane2 = reservation.getTickets().get(numberOfPassengers).getSeatDetail().getFlightPlane();
        }
        else {
            flightPlane1 = reservation.getTickets().get(0).getSeatDetail().getFlightPlane();
            numberOfPassengers = reservation.getTickets().size();
        }

        Duration duration = Duration.between(flightPlane1.getDepartureTime(), flightPlane1.getArrivalTime());
        this.duration1 = duration.toHours() + "h " + duration.toMinutesPart() + "m";
        if (isTwoWay) {
            duration = Duration.between(flightPlane2.getDepartureTime(), flightPlane2.getArrivalTime());
            this.duration2 = duration.toHours() + "h " + duration.toMinutesPart() + "m";
        }
        this.reservation = reservation;

        updatePassengers();
    }

    private void updatePassengers() {
        if (isTwoWay) {
            for (int i = 0; i < numberOfPassengers; i++) {
                ReservationPassengerDTO passenger = new ReservationPassengerDTO();
                passenger.setName(reservation.getTickets().get(i).getPassenger().getLastName() + ' ' + reservation.getTickets().get(i).getPassenger().getFirstName());
                passenger.setSeat1(reservation.getTickets().get(i).getSeatDetail().getSeat().getSeatNumber());
                passenger.setTicket1Id(reservation.getTickets().get(i).getTicketId());
                passenger.setBaggage1(reservation.getTickets().get(i).getBaggage() == null ? "No extra baggage" : reservation.getTickets().get(i).getBaggage().getBaggageName() + " - " + reservation.getTickets().get(i).getBaggage().getWeight());
                passenger.setService1(reservation.getTickets().get(i).getService() == null ? "No extra service" : reservation.getTickets().get(i).getService().getServiceName());
                passenger.setSeat2(reservation.getTickets().get(i + numberOfPassengers).getSeatDetail().getSeat().getSeatNumber());
                passenger.setTicket2Id(reservation.getTickets().get(i + numberOfPassengers).getTicketId());
                passenger.setBaggage2(reservation.getTickets().get(i + numberOfPassengers).getBaggage() == null ? "No extra baggage" : reservation.getTickets().get(i).getBaggage().getBaggageName() + " - " + reservation.getTickets().get(i).getBaggage().getWeight());
                passenger.setService2(reservation.getTickets().get(i + numberOfPassengers).getService() == null ? "No extra service" : reservation.getTickets().get(i).getService().getServiceName());
                passengers.add(passenger);
            }
        }
        else {
            for (int i = 0; i < numberOfPassengers; i++) {
                ReservationPassengerDTO passenger = new ReservationPassengerDTO();
                passenger.setName(reservation.getTickets().get(i).getPassenger().getLastName() + ' ' + reservation.getTickets().get(i).getPassenger().getFirstName());
                passenger.setSeat1(reservation.getTickets().get(i).getSeatDetail().getSeat().getSeatNumber());
                passenger.setTicket1Id(reservation.getTickets().get(i).getTicketId());
                passenger.setBaggage1(reservation.getTickets().get(i).getBaggage() == null ? "No extra baggage" : reservation.getTickets().get(i).getBaggage().getBaggageName() + " - " + reservation.getTickets().get(i).getBaggage().getWeight());
                passenger.setService1(reservation.getTickets().get(i).getService() == null ? "No extra service" : reservation.getTickets().get(i).getService().getServiceName());
                passengers.add(passenger);
            }
        }
    }
}
