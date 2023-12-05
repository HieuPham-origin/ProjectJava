package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Table(name = "Ticket_class")
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class TicketClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private int classId;

    @Column(name = "class_name")
    private String className;

    @Column(name = "rate")
    private int rate;

    @Column(name = "baggage")
    private int baggage;

    @Column(name = "cabin_baggage")
    private int cabinBaggage;
}

