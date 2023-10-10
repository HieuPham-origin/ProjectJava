package com.example.Final.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Baggage")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Baggage {
    @Id
    @Column(name="baggage_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int baggage_id;
    private int weight;

}
