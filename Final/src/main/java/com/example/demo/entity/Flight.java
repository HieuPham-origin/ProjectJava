package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Table(name = "Flight")
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id")
    private int flightId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "departure_airport_id", referencedColumnName = "airport_id")
    private Airport departureAirport;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "arrival_airport_id", referencedColumnName = "airport_id")
    private Airport arrivalAirport;
<<<<<<< HEAD
=======

    @Column(name = "status")
    private String status;

    @Column(name = "price")
    private double price;
}
>>>>>>> 6ad1c308d5eedc874c064631d0094ee765482c1a

    @Column(name = "flight_airline")
    private String flightAirline;

    @Column(name = "flight_price")
    private int flightPrice;

    @Column(name="flight_number")
    private String flightNumber;
}