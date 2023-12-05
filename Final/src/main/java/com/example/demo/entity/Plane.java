package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Plane")
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class Plane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plane_id")
    private int planeId;

    @Column(name = "plane_name")
    private String planeName;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "status")
    private String status;
}
