package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.domain.model.RecuperarSenhaCodigo;
import com.example.segundoapiappfixa.infrastructure.database.redis.entity.RecuperarSenhaCodigoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecuperarSenhaCodigoMapper {

    RecuperarSenhaCodigoEntity toEntity(RecuperarSenhaCodigo model);
    RecuperarSenhaCodigo toModel(RecuperarSenhaCodigoEntity entity);

}
