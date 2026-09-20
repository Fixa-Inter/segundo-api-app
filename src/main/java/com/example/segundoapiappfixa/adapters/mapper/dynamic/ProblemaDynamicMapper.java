package com.example.segundoapiappfixa.adapters.mapper.dynamic;

import com.example.segundoapiappfixa.adapters.dto.output.Problema.ProblemaOutputDTO;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ProblemaDynamicMapper {
    public ProblemaOutputDTO mask(ProblemaOutputDTO dto, List<String> fields) {
        return new ProblemaOutputDTO(
                fields.contains("id") ? dto.id() : null,
                fields.contains("titulo") ? dto.titulo() : null,
                fields.contains("dataCriacao") ? dto.dataCriacao() : null,
                fields.contains("nomeUsuario") ? dto.nomeUsuario() : null);
    }
}
