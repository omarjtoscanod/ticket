package com.doublev.ticket.controller;

import com.doublev.ticket.dto.TicketRequest;
import com.doublev.ticket.dto.TicketResponse;
import com.doublev.ticket.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
@Tag(name = "Tickets", description = "API para gestión de tickets")
public class TicketController {
  private final TicketService ticketService;

  @GetMapping
  @Operation(summary = "Obtener tickets paginados")
  @ApiResponse(responseCode = "200", description = "Lista de tickets encontrada")
  public ResponseEntity<Page<TicketResponse>> getAllTickets(
      @RequestParam(name = "usuario", required = false) String usuario,
      @PageableDefault(size = 10) Pageable pageable) {
    return ResponseEntity.ok(ticketService.getAllTickets(usuario, pageable));
  }

  @PostMapping
  @Operation(summary = "Crear un nuevo ticket")
  @ApiResponse(responseCode = "201", description = "Ticket creado exitosamente")
  public ResponseEntity<TicketResponse> createTicket(@Valid @RequestBody TicketRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.createTicket(request));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Actualizar un ticket existente")
  @ApiResponse(responseCode = "200", description = "Ticket actualizado")
  public ResponseEntity<TicketResponse> updateTicket(
      @PathVariable("id") Long id, @Valid @RequestBody TicketRequest request) {
    return ResponseEntity.ok(ticketService.updateTicket(id, request));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar un ticket")
  @ApiResponse(responseCode = "204", description = "Ticket eliminado")
  public ResponseEntity<Void> deleteTicket(@PathVariable("id") Long id) {
    ticketService.deleteTicket(id);
    return ResponseEntity.noContent().build();
  }
}
