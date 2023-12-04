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

<<<<<<< HEAD
    @Column(name = "ticket_class_price")
    private String price;
=======
    @Column(name = "price")
    private int price;
>>>>>>> 6ad1c308d5eedc874c064631d0094ee765482c1a
}

