package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.domain.model.Endereco;
import com.example.segundoapiappfixa.domain.model.LocalEndereco;
import com.example.segundoapiappfixa.infrastructure.database.entity.LocalEnderecoEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocalEnderecoMapper {

    LocalEndereco toModel(LocalEnderecoEntity entity);
    LocalEnderecoEntity toEntity(LocalEndereco model);

}
