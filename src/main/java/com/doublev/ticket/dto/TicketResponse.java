package com.doublev.ticket.dto;

import com.doublev.ticket.model.Status;
import java.time.LocalDateTime;

public record TicketResponse(
    Long id,
    String usuario,
    Status estatus,
    LocalDateTime fechaCreacion,
    LocalDateTime fechaActualizacion
) {}