package com.example.demo.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Passenger")
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id")
    private int passengerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "country")
    private String country;

    @Column(name = "type")
    private int type;

    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "type_id", insertable = false, updatable = false)
    private CustomerType customerType;

    // Getters and setters
}

