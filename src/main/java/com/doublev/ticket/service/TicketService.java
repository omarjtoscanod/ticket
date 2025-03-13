package com.doublev.ticket.service;

import com.doublev.ticket.dto.TicketRequest;
import com.doublev.ticket.dto.TicketResponse;
import com.doublev.ticket.exception.TicketNotFoundException;
import com.doublev.ticket.mapper.TicketMapper;
import com.doublev.ticket.model.Ticket;
import com.doublev.ticket.repository.TicketRepository;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketService {

  private final TicketRepository ticketRepository;
  private final TicketMapper ticketMapper;

  @Transactional(readOnly = true)
  public Page<TicketResponse> getAllTickets(String usuario, Pageable pageable) {
    Page<Ticket> ticketsPage =
        (usuario != null && !usuario.isEmpty())
            ? ticketRepository.findByUsuarioContainingIgnoreCase(usuario, pageable)
            : ticketRepository.findAll(pageable);

    return new PageImpl<>(
        ticketsPage.getContent().stream()
            .map(ticketMapper::entityToResponse)
            .collect(Collectors.toList()),
        pageable,
        ticketsPage.getTotalElements());
  }

  @Transactional
  public TicketResponse createTicket(TicketRequest request) {
    Ticket ticket = ticketMapper.requestToEntity(request);
    return ticketMapper.entityToResponse(ticketRepository.save(ticket));
  }

  @Transactional
  public TicketResponse updateTicket(Long id, TicketRequest request) {
    Ticket existingTicket =
        ticketRepository
            .findById(id)
            .orElseThrow(() -> new TicketNotFoundException("Ticket no encontrado con ID: " + id));

    existingTicket.setUsuario(request.usuario());
    existingTicket.setEstatus(request.estatus());

    return ticketMapper.entityToResponse(ticketRepository.save(existingTicket));
  }

  @Transactional
  public void deleteTicket(Long id) {
    if (!ticketRepository.existsById(id)) {
      throw new TicketNotFoundException("Ticket no encontrado con ID: " + id);
    }
    ticketRepository.deleteById(id);
  }
}
