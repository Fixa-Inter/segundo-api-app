package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.domain.model.Equipamento;
import com.example.segundoapiappfixa.adapters.dto.output.Equipamento.EquipamentoOutputDTO;
import com.example.segundoapiappfixa.infrastructure.database.entity.EquipamentoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EquipamentoMapper {

    EquipamentoEntity toEntity(Equipamento model);
    Equipamento toModel(EquipamentoEntity entity);

    @Mapping(source = "modeloEquipamento.nome", target = "modeloEquipamento")
    @Mapping(source = "localEndereco.nome", target = "localEndereco")
    @Mapping(source = "usuario.nomeCompleto", target = "usuario")
    EquipamentoOutputDTO toOutputDTO(Equipamento equipamento);

}
