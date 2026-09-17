package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.adapters.dto.input.Problema.ProblemaCriarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Problema.ProblemaOutputDTO;
import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.infrastructure.database.entity.ProblemaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProblemaMapper {

    Problema toModel(ProblemaEntity entity);
    ProblemaEntity toEntity(Problema problema);

    @Mapping(source = "usuario.nomeCompleto", target = "nomeUsuario")
    ProblemaOutputDTO toOutputDTO(Problema problema);

}
