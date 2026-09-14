package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.domain.model.Endereco;
import com.example.segundoapiappfixa.infrastructure.database.entity.EnderecoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    Endereco toModel(EnderecoEntity entity);
    EnderecoEntity toEntity(Endereco model);

}
