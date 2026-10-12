package com.example.segundoapiappfixa.adapters.mapper.dynamic;

import com.example.segundoapiappfixa.adapters.dto.output.ObservacaoConclusao.ObservacaoConclusaoOutputDTO;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ObservacaoConclusaoDynamicMapper {
    public ObservacaoConclusaoOutputDTO mask(ObservacaoConclusaoOutputDTO dto, List<String> fields) {
        return new ObservacaoConclusaoOutputDTO(
                fields.contains("id") ? dto.id() : null,
                fields.contains("titulo") ? dto.titulo() : null,
                fields.contains("descricaoProblema") ? dto.descricaoProblema() : null,
                fields.contains("categoriaProblema") ? dto.categoriaProblema() : null,
                fields.contains("categoriaEquipamento") ? dto.categoriaEquipamento() : null,
                fields.contains("localEndereco") ? dto.localEndereco() : null,
                fields.contains("descricaoLocal") ? dto.descricaoLocal() : null,
                fields.contains("statusProblema") ? dto.statusProblema() : null,
                fields.contains("prioridade") ? dto.prioridade() : null,
                fields.contains("statusOrdemServico") ? dto.statusOrdemServico() : null,
                fields.contains("nomeResponsavel") ? dto.nomeResponsavel() : null,
                fields.contains("dificuldade") ? dto.dificuldade() : null,
                fields.contains("observacao") ? dto.observacao() : null,
                fields.contains("dataCriacao") ? dto.dataCriacao() : null,
                fields.contains("fotos") ? dto.fotos() : null
        );
    }
}
