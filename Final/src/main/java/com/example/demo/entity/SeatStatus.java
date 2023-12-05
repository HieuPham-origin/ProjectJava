package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Table(name = "SeatStatus")
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class SeatStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "is_taken")
    private boolean isTaken;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seat_id", referencedColumnName = "seat_id", insertable = false, updatable = false)
    private Seat seat;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passenger_id", referencedColumnName = "passenger_id", insertable = false, updatable = false)
    private Passenger passenger;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_plane_id", referencedColumnName = "id", insertable = false, updatable = false)
    private FlightPlane flightPlane;
}

