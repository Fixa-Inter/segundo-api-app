package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.domain.model.Tarefa;
import com.example.segundoapiappfixa.infrastructure.database.entity.TarefaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefaMapper {

    TarefaEntity toEntity(Tarefa model);
    Tarefa toModel(TarefaEntity entity);

}
