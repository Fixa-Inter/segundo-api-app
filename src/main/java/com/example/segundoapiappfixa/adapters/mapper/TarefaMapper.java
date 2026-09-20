package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.adapters.dto.output.Tarefa.TarefaOutputDTO;
import com.example.segundoapiappfixa.domain.model.Tarefa;
import com.example.segundoapiappfixa.infrastructure.database.entity.TarefaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TarefaMapper {

    TarefaEntity toEntity(Tarefa model);
    Tarefa toModel(TarefaEntity entity);

    @Mapping(source = "tarefa.ordemServico.problema.titulo", target = "tituloOrdemServico")
    @Mapping(source = "tarefa.ordemServico.usuario.nomeCompleto", target = "usuarioResponsavel")
    @Mapping(source = "tarefa.titulo", target = "tituloTarefa")
    TarefaOutputDTO toOutputDTO(Tarefa tarefa);

}
