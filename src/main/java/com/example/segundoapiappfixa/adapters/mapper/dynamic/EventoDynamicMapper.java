package com.example.segundoapiappfixa.adapters.mapper.dynamic;

import com.example.segundoapiappfixa.adapters.dto.output.Evento.EventoOutputDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventoDynamicMapper {
    public EventoOutputDTO mask(EventoOutputDTO dto, List<String> fields) {
        return new EventoOutputDTO(
                fields.contains("id") ? dto.id() : null,
                fields.contains("titulo") ? dto.titulo() : null,
                fields.contains("descricao") ? dto.descricao() : null,
                fields.contains("localEndereco") ? dto.localEndereco() : null,
                fields.contains("descricaoLocal") ? dto.descricaoLocal() : null,
                fields.contains("dataHoraInicio") ? dto.dataHoraInicio() : null,
                fields.contains("dataHoraFim") ? dto.dataHoraFim() : null,
                fields.contains("observacao") ? dto.observacao() : null,
                fields.contains("usuario") ? dto.usuario() : null,
                fields.contains("dataCriacao") ? dto.dataCriacao() : null
        );
    }
}
