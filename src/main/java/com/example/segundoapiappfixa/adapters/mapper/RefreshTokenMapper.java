package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.domain.model.RefreshToken;
import com.example.segundoapiappfixa.infrastructure.database.redis.entity.RefreshTokenEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RefreshTokenMapper {
    RefreshTokenEntity toEntity(RefreshToken model);
    RefreshToken toModel(RefreshTokenEntity entity);
}
