package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.domain.model.LocalEndereco;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.LocalEnderecoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocalEnderecoMapper {

    LocalEnderecoEntity toEntity(LocalEndereco model);
    LocalEndereco toModel(LocalEnderecoEntity entity);

}
