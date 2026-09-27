package com.example.segundoapiappfixa.adapters.mapper.dynamic;

import com.example.segundoapiappfixa.adapters.dto.output.Tarefa.TarefaOutputDTO;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class TarefaDynamicMapper {
    public TarefaOutputDTO mask(TarefaOutputDTO dto, List<String> fields) {
        return new TarefaOutputDTO(
                fields.contains("id") ? dto.id() : null,
                fields.contains("titulo") ? dto.titulo() : null,
                fields.contains("descricao") ? dto.descricao() : null,
                fields.contains("status") ? dto.status() : null,
                fields.contains("usuarioResponsavel") ? dto.usuarioResponsavel() : null,
                fields.contains("dataCriacao") ? dto.dataCriacao() : null);
    }
}
