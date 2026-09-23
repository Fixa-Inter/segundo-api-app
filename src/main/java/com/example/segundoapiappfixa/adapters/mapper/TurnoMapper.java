package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.adapters.dto.output.Turno.TurnoOutputDTO;
import com.example.segundoapiappfixa.domain.model.Turno;
import com.example.segundoapiappfixa.infrastructure.database.entity.TurnoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TurnoMapper {

    Turno toModel(TurnoEntity entity);

    TurnoOutputDTO toOutputDTO(Turno turno);
}
