package com.example.segundoapiappfixa.adapters.mapper.dynamic;

import com.example.segundoapiappfixa.adapters.dto.output.MarcaEquipamento.MarcaEquipamentoOutputDTO;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class MarcaEquipamentoDynamicMapper {
    public MarcaEquipamentoOutputDTO mask(MarcaEquipamentoOutputDTO dto, List<String> fields) {
        return new MarcaEquipamentoOutputDTO(
                fields.contains("id") ? dto.id() : null,
                fields.contains("nome") ? dto.nome() : null,
                fields.contains("descricao") ? dto.descricao() : null,
                fields.contains("dataCriacao") ? dto.dataCriacao() : null,
                fields.contains("estaAtivo") ? dto.estaAtivo() : null
        );
    }
}
