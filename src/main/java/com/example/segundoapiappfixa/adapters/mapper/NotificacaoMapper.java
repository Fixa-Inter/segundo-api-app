package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.adapters.dto.output.Notificacao.NotificacaoOutputDTO;
import com.example.segundoapiappfixa.domain.model.Notificacao;
import com.example.segundoapiappfixa.infrastructure.database.redis.entity.NotificacaoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificacaoMapper {
    NotificacaoEntity toEntity(Notificacao model);
    Notificacao toModel(NotificacaoEntity entity);
    NotificacaoOutputDTO toOutputDTO(Notificacao notificacao);
}
