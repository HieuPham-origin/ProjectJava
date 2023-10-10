package com.example.Final.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="Promotion")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {
    @Id
    @Column(name="promotion_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int promotion_id;
    private String detail;
    private int type_promotion;
    private Date start_day;
    private Date end_day;
}
