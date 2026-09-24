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
                fields.contains("descricaoProblema") ? dto.descricaoProblema() : null,
                fields.contains("categoriaEquipamento") ? dto.categoriaEquipamento() : null,
                fields.contains("localEndereco") ? dto.localEndereco() : null,
                fields.contains("descricaoLocal") ? dto.descricaoLocal() : null,
                fields.contains("status") ? dto.status() : null,
                fields.contains("usuario") ? dto.usuario() : null,
                fields.contains("tipoAcesso") ? dto.tipoAcesso() : null,
                fields.contains("dataCriacao") ? dto.dataCriacao() : null,
                fields.contains("fotos") ? dto.fotos() : null);
    }
}
