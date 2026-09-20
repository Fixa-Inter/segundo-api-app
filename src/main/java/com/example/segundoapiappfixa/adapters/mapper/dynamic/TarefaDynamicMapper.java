package com.example.segundoapiappfixa.adapters.mapper.dynamic;

import com.example.segundoapiappfixa.adapters.dto.output.Tarefa.TarefaOutputDTO;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class TarefaDynamicMapper {
    public TarefaOutputDTO mask(TarefaOutputDTO dto, List<String> fields) {
        return new TarefaOutputDTO(
                fields.contains("id") ? dto.id() : null,
                fields.contains("tituloTarefa") ? dto.tituloTarefa() : null,
                fields.contains("tituloOrdemServico") ? dto.tituloOrdemServico() : null,
                fields.contains("usuarioResponsavel") ? dto.usuarioResponsavel() : null,
                fields.contains("dataCriacao") ? dto.dataCriacao() : null);
    }
}
