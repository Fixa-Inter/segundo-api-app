package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.adapters.dto.output.OrdemServico.OrdemServicoOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Problema.ProblemaOutputDTO;
import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.infrastructure.database.entity.OrdemServicoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrdemServicoMapper {

    OrdemServicoEntity toEntity(OrdemServico model);
    OrdemServico toModel(OrdemServicoEntity entity);

    @Mapping(source = "problema.titulo", target = "titulo")
    @Mapping(source = "usuario.nomeCompleto", target = "nomeUsuario")
    @Mapping(source = "usuario.tipoAcesso", target = "tipoAcesso")
    OrdemServicoOutputDTO toOutputDTO(OrdemServico ordemServico);
}
