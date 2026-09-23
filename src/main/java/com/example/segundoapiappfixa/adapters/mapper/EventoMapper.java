package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.adapters.dto.output.Evento.EventoOutputDTO;
import com.example.segundoapiappfixa.domain.model.Evento;
import com.example.segundoapiappfixa.infrastructure.database.entity.EventoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventoMapper {

    EventoEntity toEntity(Evento model);
    Evento toModel(EventoEntity entity);

    @Mapping(source = "localEndereco.nome", target = "localEndereco")
    @Mapping(source = "usuario.nomeCompleto", target = "usuario")
    EventoOutputDTO toOutputDTO(Evento evento);
}
