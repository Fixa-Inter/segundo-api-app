package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.domain.model.Unidade;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.UnidadeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UnidadeMapper {

    UnidadeEntity toEntity(Unidade model);
    Unidade toModel(UnidadeEntity entity);

}
