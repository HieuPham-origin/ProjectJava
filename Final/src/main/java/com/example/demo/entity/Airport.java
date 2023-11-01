package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Table(name = "Airport")
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airport_id")
    private int airportId;

    @Column(name = "airport_name")
    private String airportName;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;
}

