package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.adapters.dto.output.MarcaEquipamento.MarcaEquipamentoOutputDTO;
import com.example.segundoapiappfixa.domain.model.MarcaEquipamento;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.MarcaEquipamentoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarcaEquipamentoMapper {
    MarcaEquipamentoEntity toEntity(MarcaEquipamento model);
    MarcaEquipamento toModel(MarcaEquipamentoEntity entity);

    MarcaEquipamentoOutputDTO toOutputDTO(MarcaEquipamento model);
}
