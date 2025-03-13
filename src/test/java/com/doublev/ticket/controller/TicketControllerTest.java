// TicketControllerTest.java
package com.doublev.ticket.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.doublev.ticket.dto.TicketRequest;
import com.doublev.ticket.dto.TicketResponse;
import com.doublev.ticket.model.Status;
import com.doublev.ticket.service.TicketService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class TicketControllerTest {

  @Mock private TicketService ticketService;

  @InjectMocks private TicketController ticketController;

  @Test
  void createTicket_ShouldReturnCreatedStatus() {
    // Arrange
    TicketRequest request = new TicketRequest("user1", Status.ABIERTO);
    TicketResponse response = new TicketResponse(1L, "user1", Status.ABIERTO, null, null);
    when(ticketService.createTicket(request)).thenReturn(response);

    // Act
    ResponseEntity<TicketResponse> result = ticketController.createTicket(request);

    // Assert
    assertEquals(HttpStatus.CREATED, result.getStatusCode());
    assertEquals(1L, result.getBody().id());
  }
}
