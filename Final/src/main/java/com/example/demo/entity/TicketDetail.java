package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Table(name = "Ticket_Detail")
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class TicketDetail {
    @Id
    @Column(name = "type_id")
    private int typeId;

    @Id
    @Column(name = "ticket_id")
    private int ticketId;

    @Column(name = "price")
    private int price;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "type_id", insertable = false, updatable = false)
    private CustomerType customerType;

    @ManyToOne
    @JoinColumn(name = "ticket_id", referencedColumnName = "ticket_id", insertable = false, updatable = false)
    private Ticket ticket;
}

