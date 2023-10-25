package com.example.demo.Model;

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

    @Column(name = "flight_id")
    private int flightId;

    @Column(name = "plane_id")
    private int planeId;

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

    @Column(name = "flight_type")
    private int flightType;

    @ManyToOne
    @JoinColumn(name = "flight_id", referencedColumnName = "flight_id", insertable = false, updatable = false)
    private Flight flight;

    @ManyToOne
    @JoinColumn(name = "plane_id", referencedColumnName = "plane_id", insertable = false, updatable = false)
    private Plane plane;
}

