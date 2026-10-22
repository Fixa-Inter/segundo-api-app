package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.adapters.dto.output.Ocorrencia.OcorrenciaOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.input.Ocorrencia.OcorrenciaCadastrarInputDTO;
import com.example.segundoapiappfixa.domain.model.Ocorrencia;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.OcorrenciaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = FotoMapper.class)
public interface OcorrenciaMapper {

    OcorrenciaEntity toEntity(Ocorrencia ocorrencia);
    Ocorrencia toModel(OcorrenciaEntity ocorrenciaEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(source = "localUnidadeId", target = "localUnidade.id")
    @Mapping(source = "categoriaEquipamentoId", target = "categoriaEquipamento.id")
    @Mapping(source = "categoriaProblemaId", target = "categoriaProblema")
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "estaAtivo", ignore = true)
    @Mapping(target = "fotos", ignore = true)
    Ocorrencia toModel(OcorrenciaCadastrarInputDTO input);

    @Mapping(source = "categoriaProblema.nome", target = "categoriaProblema")
    @Mapping(source = "prioridade.nome", target = "prioridade")
    @Mapping(source = "localUnidade.nome", target = "localUnidade")
    @Mapping(source = "categoriaEquipamento.nome", target = "categoriaEquipamento")
    @Mapping(source = "usuario.nomeCompleto", target = "nomeUsuario")
    @Mapping(source = "usuario.tipoAcesso.nome", target = "tipoAcesso")
    @Mapping(source = "fotos", target = "fotos")
    OcorrenciaOutputDTO toOutputDTO(Ocorrencia ocorrencia);
}
