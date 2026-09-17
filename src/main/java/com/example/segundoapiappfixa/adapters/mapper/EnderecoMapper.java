package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.domain.model.Endereco;
import com.example.segundoapiappfixa.infrastructure.database.entity.EnderecoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    EnderecoEntity toEntity(Endereco model);
    Endereco toModel(EnderecoEntity entity);

}
