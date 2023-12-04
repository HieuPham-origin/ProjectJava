package com.example.demo.entity;

import java.sql.Time;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

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
    private Time departureTime;

    @Column(name = "arrival_time")
    private Time arrivalTime;

    @Column(name = "departure_day")
    @Temporal(TemporalType.DATE)
    private Date departureDay;

    @Column(name = "arrival_day")
    @Temporal(TemporalType.DATE)
    private Date arrivalDay;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_id", referencedColumnName = "flight_id", insertable = false, updatable = false)
    private Flight flight;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "plane_id", referencedColumnName = "plane_id", insertable = false, updatable = false)
    private Plane plane;
}

