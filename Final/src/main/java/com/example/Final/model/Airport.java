package com.example.Final.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Airport")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Airport {
    @Id
    @Column(name="airport_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int airport_id;
    private String airport_name;
    private String address;
}
