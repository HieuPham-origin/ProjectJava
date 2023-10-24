package com.example.Final.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.Date;

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

    @Column(name = "seat_status")
    private String seatStatus;

    @Column(name = "plane_id")
    private int planeId;

    @ManyToOne
    @JoinColumn(name = "plane_id", referencedColumnName = "plane_id", insertable = false, updatable = false)
    private Plane plane;
}

