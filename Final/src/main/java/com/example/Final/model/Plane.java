package com.example.Final.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

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
}
