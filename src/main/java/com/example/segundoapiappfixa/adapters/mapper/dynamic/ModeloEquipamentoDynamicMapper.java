package com.example.segundoapiappfixa.adapters.mapper.dynamic;

import com.example.segundoapiappfixa.adapters.dto.output.ModeloEquipamento.ModeloEquipamentoOutputDTO;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ModeloEquipamentoDynamicMapper {
    public ModeloEquipamentoOutputDTO mask(ModeloEquipamentoOutputDTO dto, List<String> fields) {
        return new ModeloEquipamentoOutputDTO(
                fields.contains("id") ? dto.id() : null,
                fields.contains("nome") ? dto.nome() : null,
                fields.contains("descricao") ? dto.descricao() : null,
                fields.contains("marca") ? dto.marca() : null,
                fields.contains("categoria") ? dto.categoria() : null,
                fields.contains("dataCriacao") ? dto.dataCriacao() : null,
                fields.contains("estaAtivo") ? dto.estaAtivo() : null
        );
    }
}
