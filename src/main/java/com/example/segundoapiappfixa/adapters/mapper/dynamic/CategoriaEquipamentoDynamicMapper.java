package com.example.segundoapiappfixa.adapters.mapper.dynamic;

import com.example.segundoapiappfixa.adapters.dto.output.CategoriaEquipamento.CategoriaEquipamentoOutputDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoriaEquipamentoDynamicMapper {

    public CategoriaEquipamentoOutputDTO mask(CategoriaEquipamentoOutputDTO dto, List<String> fields) {
        return new CategoriaEquipamentoOutputDTO(
                fields.contains("id") ? dto.id(): null,
                fields.contains("nome") ? dto.nome(): null,
                fields.contains("descricao") ? dto.descricao(): null,
                fields.contains("nomeUsuario") ? dto.nomeUsuario(): null,
                fields.contains("dataCriacao") ? dto.dataCriacao(): null,
                fields.contains("estaAtivo") ? dto.estaAtivo(): null
        );
    }
}
