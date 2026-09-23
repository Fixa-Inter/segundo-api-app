package com.example.segundoapiappfixa.adapters.mapper.dynamic;

import com.example.segundoapiappfixa.adapters.dto.output.Aptidao.AptidaoOutputDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AptidaoDynamicMapper {
    public AptidaoOutputDTO mask(AptidaoOutputDTO dto, List<String> fields) {
        return new AptidaoOutputDTO(
                fields.contains("id") ? dto.id() : null,
                fields.contains("categoriaProblema") ? dto.categoriaProblema() : null,
                fields.contains("nota") ? dto.nota() : null,
                fields.contains("dataCriacao") ? dto.dataCriacao() : null,
                fields.contains("estaAtivo") ? dto.estaAtivo() : null
        );
    }
}
