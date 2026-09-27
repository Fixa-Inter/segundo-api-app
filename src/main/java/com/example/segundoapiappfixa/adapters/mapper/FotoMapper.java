package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.adapters.dto.output.Foto.FotoOutputDTO;
import com.example.segundoapiappfixa.domain.model.Foto;
import com.example.segundoapiappfixa.infrastructure.database.entity.FotoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FotoMapper {

    @Mapping(target = "problema", ignore = true)
    @Mapping(target = "ocorrencia", ignore = true)
    @Mapping(target = "ordemServico", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    FotoEntity toEntity(Foto foto);

    @Mapping(target = "problema", ignore = true)
    @Mapping(target = "ocorrencia", ignore = true)
    @Mapping(target = "ordemServico", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Foto toModel(FotoEntity entity);

    FotoOutputDTO toOutputDTO(Foto foto);
}
