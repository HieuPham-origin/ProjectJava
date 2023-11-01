package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

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

    @Column(name = "gender", nullable = true)
    private String gender;

    @Column(name = "phone_number", nullable = true)
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "address", nullable = true)
    private String address;

    @Column(name = "country", nullable = true)
    private String country;

    @Column(name = "type")
    private int type;

    @ManyToOne(optional = true)
    @JoinColumn(name = "type", referencedColumnName = "type_id", insertable = false, updatable = false)
    private CustomerType customerType;
}

