// TicketMapper.java
package com.doublev.ticket.mapper;

import com.doublev.ticket.dto.TicketRequest;
import com.doublev.ticket.dto.TicketResponse;
import com.doublev.ticket.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TicketMapper {
  TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "fechaCreacion", ignore = true)
  @Mapping(target = "fechaActualizacion", ignore = true)
  Ticket requestToEntity(TicketRequest request);

  TicketResponse entityToResponse(Ticket ticket);
}
