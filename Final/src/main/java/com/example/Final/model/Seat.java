package com.example.Final.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Seat")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    @Id
    @Column(name="seat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seat_id;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plane_id")
    private Plane plane;
}
