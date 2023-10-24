package com.example.Final.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.Date;

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

    @Column(name = "flight_number")
    private String flightNumber;

    @Column(name = "departure_airport_id")
    private int departureAirportId;

    @Column(name = "arrival_airport_id")
    private int arrivalAirportId;

    @ManyToOne
    @JoinColumn(name = "departure_airport_id", referencedColumnName = "airportId", insertable = false, updatable = false)
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "arrival_airport_id", referencedColumnName = "airportId", insertable = false, updatable = false)
    private Airport arrivalAirport;
}

