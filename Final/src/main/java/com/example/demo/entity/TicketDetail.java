package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Table(name = "Ticket_Detail")
public class TicketDetail {
    @EmbeddedId
    private TicketDetailId ticketDetailId;

    @Column(name = "price")
    private int price;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("typeId")
    @JoinColumn(name = "type_id")
    private CustomerType customerType;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("ticketId")
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
}

