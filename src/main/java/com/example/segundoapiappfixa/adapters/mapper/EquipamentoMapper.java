package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.domain.model.Equipamento;
import com.example.segundoapiappfixa.adapters.dto.output.Equipamento.EquipamentoOutputDTO;
import com.example.segundoapiappfixa.infrastructure.database.entity.EquipamentoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EquipamentoMapper {

    EquipamentoEntity toEntity(Equipamento model);
    Equipamento toModel(EquipamentoEntity entity);

    default EquipamentoOutputDTO toOutputDTO(Equipamento equipamento) {
        return new EquipamentoOutputDTO(
                equipamento.getId(),
                equipamento.getCodigo(),
                equipamento.getModeloEquipamento() != null ? equipamento.getModeloEquipamento().getNome() : null,
                equipamento.getLocalEndereco() != null ? equipamento.getLocalEndereco().getDescricao() : null,
                equipamento.getUsuario() != null ? equipamento.getUsuario().getNomeCompleto() : null,
                equipamento.getDataCriacao() != null ? equipamento.getDataCriacao().toLocalDate() : null,
                equipamento.getEstaAtivo()
        );
    }

}
