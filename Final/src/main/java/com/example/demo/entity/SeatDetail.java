package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Table(name = "seat_detail")
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class SeatDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "is_taken")
    private boolean isTaken;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seat_id", referencedColumnName = "seat_id")
    private Seat seat;

    @OneToOne(mappedBy = "seatDetail")
    private Ticket ticket;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_plane_id", referencedColumnName = "id")
    private FlightPlane flightPlane;
}

