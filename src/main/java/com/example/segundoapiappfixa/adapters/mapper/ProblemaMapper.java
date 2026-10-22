package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.adapters.dto.output.Problema.ProblemaOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.input.Problema.ProblemaCriarInputDTO;
import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.ProblemaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = FotoMapper.class)
public interface ProblemaMapper {

    @Mapping(target = "fotos", ignore = true)
    ProblemaEntity toEntity(Problema problema);
    Problema toModel(ProblemaEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(source = "categoriaEquipamentoId", target = "categoriaEquipamento.id")
    @Mapping(source = "localUnidadeID", target = "localUnidade.id")
    @Mapping(target = "motivoRecusa", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "fotos", ignore = true)
    Problema toModel(ProblemaCriarInputDTO input);

    @Mapping(source = "categoriaEquipamento.nome", target = "categoriaEquipamento")
    @Mapping(source = "localUnidade.nome", target = "localUnidade")
    @Mapping(source = "status.nome", target = "status")
    @Mapping(source = "usuario.nomeCompleto", target = "usuario")
    @Mapping(source = "usuario.tipoAcesso.nome", target = "tipoAcesso")
    @Mapping(source = "fotos", target = "fotos")
    ProblemaOutputDTO toOutputDTO(Problema problema);

}
