package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.adapters.dto.output.ModeloEquipamento.ModeloEquipamentoOutputDTO;
import com.example.segundoapiappfixa.domain.model.ModeloEquipamento;
import com.example.segundoapiappfixa.infrastructure.database.entity.ModeloEquipamentoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ModeloEquipamentoMapper {
    ModeloEquipamentoEntity toEntity(ModeloEquipamento model);
    ModeloEquipamento toModel(ModeloEquipamentoEntity entity);

    @Mapping(source = "marcaEquipamento.nome", target = "marca")
    @Mapping(source = "categoriaEquipamento.nome", target = "categoria")
    ModeloEquipamentoOutputDTO toOutputDTO(ModeloEquipamento model);
}
