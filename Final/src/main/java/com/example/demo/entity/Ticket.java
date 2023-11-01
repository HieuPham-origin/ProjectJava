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

    @Column(name = "seat_id")
    private int seatId;

    @Column(name = "class_id")
    private int classId;

    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "tax")
    private int tax;

    @Column(name = "service_id")
    private int serviceId;

    @Column(name = "baggage_id")
    private int baggageId;

    @Column(name = "status")
    private String status;

    @Column(name = "payment_id")
    private int paymentId;

    @Column(name = "day_order")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dayOrder;

    @Column(name = "day_pay")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dayPay;

    @ManyToOne
    @JoinColumn(name = "seat_id", referencedColumnName = "seat_id", insertable = false, updatable = false)
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "class_id", referencedColumnName = "class_id", insertable = false, updatable = false)
    private TicketClass ticketClass;

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "service_id", insertable = false, updatable = false)
    private Service service;

    @ManyToOne
    @JoinColumn(name = "baggage_id", referencedColumnName = "baggage_id", insertable = false, updatable = false)
    private Baggage baggage;

    @ManyToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id", insertable = false, updatable = false)
    private PaymentFormat paymentFormat;
}

