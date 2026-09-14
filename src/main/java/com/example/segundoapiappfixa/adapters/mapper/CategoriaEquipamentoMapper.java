package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.domain.model.CategoriaEquipamento;
import com.example.segundoapiappfixa.infrastructure.database.entity.CategoriaEquipamentoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriaEquipamentoMapper {

    CategoriaEquipamentoEntity toEntity(CategoriaEquipamento model);
    CategoriaEquipamento toModel(CategoriaEquipamentoEntity entity);

}
