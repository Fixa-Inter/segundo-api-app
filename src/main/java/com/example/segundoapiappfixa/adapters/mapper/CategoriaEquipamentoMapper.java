package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.adapters.dto.output.CategoriaEquipamento.CategoriaEquipamentoOutputDTO;
import com.example.segundoapiappfixa.domain.model.CategoriaEquipamento;
import com.example.segundoapiappfixa.infrastructure.database.entity.CategoriaEquipamentoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoriaEquipamentoMapper {

    CategoriaEquipamentoEntity toEntity(CategoriaEquipamento model);
    CategoriaEquipamento toModel(CategoriaEquipamentoEntity entity);

    @Mapping(source = "usuario.nomeCompleto", target = "nomeUsuario")
    @Mapping(target = "dataCriacao", expression = "java(categoriaEquipamento.getDataCriacao() == null ? null : categoriaEquipamento.getDataCriacao().toLocalDate())")
    CategoriaEquipamentoOutputDTO toOutputDTO(CategoriaEquipamento categoriaEquipamento);
}
