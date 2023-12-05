package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Seat")
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private int seatId;

    @Column(name = "seat_number")
    private String seatNumber;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "plane_id", referencedColumnName = "plane_id")
    private Plane plane;
}

