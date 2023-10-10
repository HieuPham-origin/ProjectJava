package com.example.Final.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="Passenger")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {
    @Id
    @Column(name="pasenger_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pasenger_id;
    private String passener_name;
    private String phone;
    private boolean gender;
    private Date dayofBirth;
    private String email;

}
