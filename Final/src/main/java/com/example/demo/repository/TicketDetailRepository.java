package com.example.demo.repository;

import com.example.demo.entity.TicketDetail;
import com.example.demo.entity.TicketDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketDetailRepository extends JpaRepository<TicketDetail, TicketDetailId> {
}
