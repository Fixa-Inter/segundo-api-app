package com.example.segundoapiappfixa.adapters.mapper.dynamic;

import com.example.segundoapiappfixa.adapters.dto.output.Ocorrencia.OcorrenciaOutputDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OcorrenciaDynamicMapper {

    public OcorrenciaOutputDTO mask(OcorrenciaOutputDTO dto, List<String> fields) {
        return new OcorrenciaOutputDTO(
                fields.contains("id") ? dto.id() : null,
                fields.contains("titulo") ? dto.titulo() : null,
                fields.contains("descricao") ? dto.descricaoOcorrencia() : null,
                fields.contains("categoriaProblema") ? dto.categoriaProblema() : null,
                fields.contains("prioridade") ? dto.prioridade() : null,
                fields.contains("localEndereco") ? dto.localEndereco() : null,
                fields.contains("descricaoLocal") ? dto.descricaoLocal() : null,
                fields.contains("dataCriacao") ? dto.dataCriacao() : null,
                fields.contains("equipamentoCodigo") ? dto.equipamentoCodigo() : null,
                fields.contains("nomeUsuario") ? dto.nomeUsuario() : null,
                fields.contains("tipoAcesso") ? dto.tipoAcesso() : null
        );
    }
}
