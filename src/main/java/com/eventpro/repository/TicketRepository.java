package com.eventpro.repository;

import com.eventpro.entity.tickets.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    boolean existsByCode(String code);
}
