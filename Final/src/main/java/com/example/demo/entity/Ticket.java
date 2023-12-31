package com.example.demo.entity;


import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Table(name = "Ticket")
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private int ticketId;

    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "tax")
    private int tax;

    @Column(name = "status")
    private String status;


    @Column(name = "day_order")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dayOrder;

    @Column(name = "day_pay")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dayPay;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seat_detail_id", referencedColumnName = "id")
    private SeatDetail seatDetail;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passenger_id", referencedColumnName = "passenger_id")
    private Passenger passenger;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id", referencedColumnName = "class_id")
    private TicketClass ticketClass;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_id", referencedColumnName = "service_id")
    private Service service;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "baggage_id", referencedColumnName = "baggage_id")
    private Baggage baggage;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    private Reservation reservation;
}

