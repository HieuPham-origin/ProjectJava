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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seat_id", referencedColumnName = "seat_id", insertable = false, updatable = false)
    private Seat seat;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id", referencedColumnName = "class_id", insertable = false, updatable = false)
    private TicketClass ticketClass;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_id", referencedColumnName = "service_id", insertable = false, updatable = false)
    private Service service;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "baggage_id", referencedColumnName = "baggage_id", insertable = false, updatable = false)
    private Baggage baggage;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id", insertable = false, updatable = false)
    private PaymentFormat paymentFormat;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Reservation reservation;
}

