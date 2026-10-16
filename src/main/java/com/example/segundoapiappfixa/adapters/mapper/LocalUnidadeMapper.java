package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.domain.model.LocalUnidade;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.LocalUnidadeEntity;
import com.example.segundoapiappfixa.adapters.dto.output.LocalEndereco.LocalUnidadeOutputDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocalUnidadeMapper {

    LocalUnidadeEntity toEntity(LocalUnidade model);
    LocalUnidade toModel(LocalUnidadeEntity entity);

    LocalUnidadeOutputDTO toOutputDTO(LocalUnidade model);

}
