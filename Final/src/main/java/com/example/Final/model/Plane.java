package com.example.Final.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Plane")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Plane {
    @Id
    @Column(name="plane_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int plane_id;
    private String plane_name;
    private int capacity;
}
