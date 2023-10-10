package com.example.Final.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Serive")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Service {
    @Id
    @Column(name="service_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int service_id;
    private String service_name;
    private String detail;
    private int price;
}
