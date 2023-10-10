package com.example.Final.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="Flight")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
    @Column(name="flight_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int flight_id;
    private String flight_name;
    private Date departure_time;
    private Date arrived_time;

}
