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
        if (reservation.getTickets().size() % 2 != 0) {
            numberOfPassengers = 1;
            isTwoWay = false;
            flightPlane1 = reservation.getTickets().get(0).getSeatDetail().getFlightPlane();
        }
        else {
            if (reservation.getTickets().size() == 2) {
                if (reservation.getTickets().get(0).getPassenger().equals(reservation.getTickets().get(1).getPassenger())) {
                    isTwoWay = true;
                    numberOfPassengers = 1;
                    flightPlane1 = reservation.getTickets().get(0).getSeatDetail().getFlightPlane();
                    flightPlane2 = reservation.getTickets().get(1).getSeatDetail().getFlightPlane();
                }
                else {
                    isTwoWay = false;
                    numberOfPassengers = 2;
                    flightPlane1 = reservation.getTickets().get(0).getSeatDetail().getFlightPlane();

                }
            }
            else {
                Passenger first = reservation.getTickets().get(0).getPassenger();
                Passenger middle = reservation.getTickets().get(reservation.getTickets().size() / 2).getPassenger();
                if (first.equals(middle)) {
                    isTwoWay = true;
                    numberOfPassengers = reservation.getTickets().size() / 2;
                    flightPlane1 = reservation.getTickets().get(0).getSeatDetail().getFlightPlane();
                    flightPlane2 = reservation.getTickets().get(reservation.getTickets().size() / 2).getSeatDetail().getFlightPlane();
                }
                else {
                    isTwoWay = false;
                    numberOfPassengers = reservation.getTickets().size() / 2;
                    flightPlane1 = reservation.getTickets().get(0).getSeatDetail().getFlightPlane();
                }
            }
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
        for (int i = 0; i < numberOfPassengers; i++) {
            ReservationPassengerDTO passenger = new ReservationPassengerDTO();
            passenger.setName(reservation.getTickets().get(0).getPassenger().getLastName() + ' ' + reservation.getTickets().get(0).getPassenger().getFirstName());
            passenger.setSeat1(reservation.getTickets().get(0).getSeatDetail().getSeat().getSeatNumber());
            passenger.setTicket1Id(reservation.getTickets().get(0).getTicketId());
            passenger.setBaggage1(reservation.getTickets().get(0).getBaggage() == null ? "No extra baggage" : reservation.getTickets().get(0).getBaggage().getBaggageName() + " - " + reservation.getTickets().get(0).getBaggage().getWeight());
            if (isTwoWay) {
                passenger.setSeat2(reservation.getTickets().get(numberOfPassengers).getSeatDetail().getSeat().getSeatNumber());
                passenger.setTicket2Id(reservation.getTickets().get(numberOfPassengers).getTicketId());
                passenger.setBaggage2(reservation.getTickets().get(numberOfPassengers).getBaggage() == null ? "No extra baggage" : reservation.getTickets().get(0).getBaggage().getBaggageName() + " - " + reservation.getTickets().get(0).getBaggage().getWeight());
            }
            passengers.add(passenger);

        }
    }
}
