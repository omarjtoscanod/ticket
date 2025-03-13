package com.doublev.ticket.dto;

import com.doublev.ticket.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TicketRequest(
    @NotBlank(message = "El usuario es obligatorio")
    String usuario,
    @NotNull(message = "El estado es obligatorio")
    Status estatus
) {}
