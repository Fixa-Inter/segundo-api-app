package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.domain.model.ExecucaoJob;
import com.example.segundoapiappfixa.infrastructure.database.redis.entity.ExecucaoJobEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExecucaoJobMapper {
    ExecucaoJobEntity toEntity(ExecucaoJob model);
    ExecucaoJob toModel(ExecucaoJobEntity entity);
}
