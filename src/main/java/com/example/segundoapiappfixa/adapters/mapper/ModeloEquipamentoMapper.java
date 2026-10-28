package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.adapters.dto.output.ModeloEquipamento.ModeloEquipamentoOutputDTO;
import com.example.segundoapiappfixa.domain.model.ModeloEquipamento;
import com.example.segundoapiappfixa.application.usecase.ModeloEquipamento.ModeloEquipamentoResumo;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.ModeloEquipamentoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ModeloEquipamentoMapper {
    ModeloEquipamentoEntity toEntity(ModeloEquipamento model);
    ModeloEquipamento toModel(ModeloEquipamentoEntity entity);

    @Mapping(source = "marcaEquipamento.nome", target = "marca")
    @Mapping(source = "categoriaEquipamento.nome", target = "categoria")
    ModeloEquipamentoOutputDTO toOutputDTO(ModeloEquipamento model);

    @Mapping(source = "modeloEquipamento.id", target = "id")
    @Mapping(source = "modeloEquipamento.nome", target = "nome")
    @Mapping(source = "modeloEquipamento.descricao", target = "descricao")
    @Mapping(source = "modeloEquipamento.marcaEquipamento.nome", target = "marca")
    @Mapping(source = "modeloEquipamento.categoriaEquipamento.nome", target = "categoria")
    @Mapping(source = "modeloEquipamento.dataCriacao", target = "dataCriacao")
    @Mapping(source = "modeloEquipamento.estaAtivo", target = "estaAtivo")
    @Mapping(source = "quantidadeAparelhos", target = "quantidadeAparelhos")
    ModeloEquipamentoOutputDTO toOutputDTO(ModeloEquipamentoResumo resumo);
}
