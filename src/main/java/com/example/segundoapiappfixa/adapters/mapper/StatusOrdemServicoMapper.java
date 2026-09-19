package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.domain.model.StatusOrdemServico;
import com.example.segundoapiappfixa.infrastructure.database.entity.StatusOrdemServicoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusOrdemServicoMapper {

    StatusOrdemServicoEntity toEntity(StatusOrdemServico model);
    StatusOrdemServico toModel(StatusOrdemServicoEntity entity);

}
