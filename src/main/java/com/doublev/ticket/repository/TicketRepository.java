package com.doublev.ticket.repository;

import com.doublev.ticket.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Page<Ticket> findByUsuarioContainingIgnoreCase(String usuario, Pageable pageable);
}
