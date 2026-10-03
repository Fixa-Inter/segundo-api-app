package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.adapters.dto.output.Aptidao.AptidaoOutputDTO;
import com.example.segundoapiappfixa.domain.model.Aptidao;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.AptidaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AptidaoMapper {

    AptidaoEntity toEntity(Aptidao model);
    Aptidao toModel(AptidaoEntity entity);

    @Mapping(source = "categoriaProblema.nome", target = "categoriaProblema")
    AptidaoOutputDTO toOutputDTO(Aptidao aptidao);

}
