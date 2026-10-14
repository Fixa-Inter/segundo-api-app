package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.domain.model.Instituicao;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.InstituicaoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstituicaoMapper {
    Instituicao toModel(InstituicaoEntity entity);
}
