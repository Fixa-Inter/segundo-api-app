package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.domain.model.TokenDispositivo;
import com.example.segundoapiappfixa.infrastructure.database.redis.entity.TokenDispositivoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenDispositivoMapper {
    TokenDispositivoEntity toEntity(TokenDispositivo model);
    TokenDispositivo toModel(TokenDispositivoEntity entity);
}
