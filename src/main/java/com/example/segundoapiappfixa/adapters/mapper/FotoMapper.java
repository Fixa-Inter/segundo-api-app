package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.domain.model.Foto;
import com.example.segundoapiappfixa.infrastructure.database.entity.FotoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FotoMapper {

    FotoEntity toEntity(Foto model);
    Foto toModel(FotoEntity entity);

}
