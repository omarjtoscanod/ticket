package com.doublev.ticket.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.doublev.ticket.dto.TicketRequest;
import com.doublev.ticket.dto.TicketResponse;
import com.doublev.ticket.exception.TicketNotFoundException;
import com.doublev.ticket.mapper.TicketMapper;
import com.doublev.ticket.model.Status;
import com.doublev.ticket.model.Ticket;
import com.doublev.ticket.repository.TicketRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

  @Mock private TicketRepository ticketRepository;

  @Mock private TicketMapper ticketMapper;

  @InjectMocks private TicketService ticketService;

  @Test
  void createTicket_ShouldReturnTicketResponse() {
    // Arrange
    TicketRequest request = new TicketRequest("user1", Status.ABIERTO);
    Ticket ticket = Ticket.builder().usuario("user1").estatus(Status.ABIERTO).build();
    TicketResponse expectedResponse =
        new TicketResponse(1L, "user1", Status.ABIERTO, LocalDateTime.now(), LocalDateTime.now());

    when(ticketMapper.requestToEntity(request)).thenReturn(ticket);
    when(ticketRepository.save(ticket)).thenReturn(ticket);
    when(ticketMapper.entityToResponse(ticket)).thenReturn(expectedResponse);

    // Act
    TicketResponse result = ticketService.createTicket(request);

    // Assert
    assertEquals(expectedResponse, result);
    verify(ticketRepository, times(1)).save(ticket);
  }

  @Test
  void updateTicket_WhenTicketExists_ShouldUpdate() {
    // Arrange
    Long id = 1L;
    TicketRequest request = new TicketRequest("user_updated", Status.CERRADO);
    Ticket existingTicket =
        Ticket.builder().id(id).usuario("user1").estatus(Status.ABIERTO).build();
    Ticket updatedTicket =
        Ticket.builder().id(id).usuario("user_updated").estatus(Status.CERRADO).build();
    TicketResponse expectedResponse =
        new TicketResponse(
            id,
            "user_updated",
            Status.CERRADO,
            existingTicket.getFechaCreacion(),
            LocalDateTime.now());

    when(ticketRepository.findById(id)).thenReturn(Optional.of(existingTicket));
    when(ticketRepository.save(any())).thenReturn(updatedTicket);
    when(ticketMapper.entityToResponse(updatedTicket)).thenReturn(expectedResponse);

    // Act
    TicketResponse result = ticketService.updateTicket(id, request);

    // Assert
    assertEquals("user_updated", result.usuario());
    assertEquals(Status.CERRADO, result.estatus());
  }

  @Test
  void deleteTicket_WhenTicketNotExists_ShouldThrowException() {
    // Arrange
    Long id = 999L;
    when(ticketRepository.existsById(id)).thenReturn(false);

    // Act & Assert
    assertThrows(TicketNotFoundException.class, () -> ticketService.deleteTicket(id));
  }
}
