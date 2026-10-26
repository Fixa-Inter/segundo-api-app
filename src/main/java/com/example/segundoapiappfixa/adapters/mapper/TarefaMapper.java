package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.adapters.dto.output.Tarefa.TarefaOutputDTO;
import com.example.segundoapiappfixa.domain.model.Tarefa;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.TarefaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = OrdemServicoMapper.class)
public interface TarefaMapper {

    @Mapping(target = "ordemServico", ignore = true)
    TarefaEntity toEntity(Tarefa model);

    Tarefa toModel(TarefaEntity entity);

    @Mapping(source = "statusOrdemServico.nome", target = "status")
    @Mapping(source = "tarefa.ordemServico.usuario.nomeCompleto", target = "usuarioResponsavel")
    TarefaOutputDTO toOutputDTO(Tarefa tarefa);

}
