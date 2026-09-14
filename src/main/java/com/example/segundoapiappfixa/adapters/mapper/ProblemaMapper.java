package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.adapters.dto.input.ProblemaCriarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.ProblemaDetalhesOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.ProblemaOutputDTO;
import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.infrastructure.database.entity.ProblemaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProblemaMapper {

    Problema toModel(ProblemaEntity entity);
//    @Mapping(source = "dataCriacao", target = "dataCriacao")
    ProblemaEntity toEntity(Problema problema);
    ProblemaEntity toEntityByCriarInputDTO(ProblemaCriarInputDTO dto);

    @Mapping(source = "usuario.nomeCompleto", target = "nomeUsuario")
    ProblemaOutputDTO toOutputDTO(Problema problema);


}
