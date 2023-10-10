package com.example.Final.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="Ticket")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @Column(name="ticket_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticket_id;
    private String ticket_name;
    private String detail;
    private int price;
    private String status;
    private Date cancel_day;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plane_id")
    private Plane plane;
}
