package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter@Setter
public class TicketDetailId implements Serializable {
    @Column(name = "type_id")
    private int typeId;

    @Column(name = "ticket_id")
    private int ticketId;;

    // Constructors, getters, and setters
}
