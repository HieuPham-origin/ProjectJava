package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Service")
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private int serviceId;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "price")
    private int price;

    @Column(name = "description")
    private String description;
}

