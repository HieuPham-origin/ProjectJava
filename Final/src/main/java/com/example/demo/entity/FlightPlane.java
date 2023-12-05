package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "Flight_Plane")
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class FlightPlane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "departure_time")
    private LocalTime departureTime;

    @Column(name = "arrival_time")
    private LocalTime arrivalTime;

    @Column(name = "departure_day")
    @Temporal(TemporalType.DATE)
    private LocalDate departureDay;

    @Column(name = "arrival_day")
    @Temporal(TemporalType.DATE)
    private LocalDate arrivalDay;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_id", referencedColumnName = "flight_id")
    private Flight flight;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "plane_id", referencedColumnName = "plane_id")
    private Plane plane;
    public long getDuration() {
        LocalDateTime departureDateTime = departureDay.atTime(departureTime);
        LocalDateTime arrivalDateTime = arrivalDay.atTime(arrivalTime);

        return Duration.between(departureDateTime, arrivalDateTime).toMinutes();
    }
    public int getPriceForSort(){
        return flight.getFlightPrice();
    }
}

