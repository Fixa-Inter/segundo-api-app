package com.example.segundoapiappfixa.adapters.mapper.dynamic;

import com.example.segundoapiappfixa.adapters.dto.output.Equipamento.EquipamentoOutputDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EquipamentoDynamicMapper {

    public EquipamentoOutputDTO mask(EquipamentoOutputDTO dto, List<String> fields) {
        return new EquipamentoOutputDTO(
                fields.contains("id") ? dto.id() : null,
                fields.contains("codigo") ? dto.codigo() : null,
                fields.contains("modeloEquipamento") ? dto.modeloEquipamento() : null,
                fields.contains("localEndereco") ? dto.localEndereco() : null,
                fields.contains("usuario") ? dto.usuario() : null,
                fields.contains("dataCriacao") ? dto.dataCriacao() : null,
                fields.contains("estaAtivo") ? dto.estaAtivo() : null
        );
    }
}
