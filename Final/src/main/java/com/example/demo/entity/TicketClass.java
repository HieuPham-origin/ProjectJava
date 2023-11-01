package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.Date;

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

    @Column(name = "description")
    private String description;
}

