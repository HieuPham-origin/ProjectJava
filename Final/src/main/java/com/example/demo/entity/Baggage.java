package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Table(name = "Baggage")
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class Baggage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "baggage_id")
    private int baggageId;

    @Column(name = "baggage_name")
    private String baggageName;

    @Column(name = "weight")
    private int weight;

    @Column(name = "price")
    private int price;
}
