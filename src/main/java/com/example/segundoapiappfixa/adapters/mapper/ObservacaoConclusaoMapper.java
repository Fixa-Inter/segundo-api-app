package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.adapters.dto.output.ObservacaoConclusao.ObservacaoConclusaoOutputDTO;
import com.example.segundoapiappfixa.domain.model.ObservacaoConclusao;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.ObservacaoConclusaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrdemServicoMapper.class, FotoMapper.class})
public interface ObservacaoConclusaoMapper {

    ObservacaoConclusaoEntity toEntity(ObservacaoConclusao model);
    ObservacaoConclusao toModel(ObservacaoConclusaoEntity entity);

    @Mapping(source = "ordemServico.problema.titulo", target = "titulo")
    @Mapping(source = "ordemServico.problema.descricaoProblema", target = "descricaoProblema")
    @Mapping(source = "ordemServico.categoriaProblema.nome", target = "categoriaProblema")
    @Mapping(source = "ordemServico.problema.categoriaEquipamento.nome", target = "categoriaEquipamento")
    @Mapping(source = "ordemServico.problema.localUnidade.nome", target = "localUnidade")
    @Mapping(source = "ordemServico.problema.descricaoLocal", target = "descricaoLocal")
    @Mapping(source = "ordemServico.problema.status.nome", target = "statusProblema")
    @Mapping(source = "ordemServico.prioridade.nome", target = "prioridade")
    @Mapping(source = "ordemServico.statusOrdemServico.nome", target = "statusOrdemServico")
    @Mapping(source = "ordemServico.usuario.nomeCompleto", target = "nomeResponsavel")
    @Mapping(source = "dificuldade.nome", target = "dificuldade")
    @Mapping(source = "fotos", target = "fotos")
    ObservacaoConclusaoOutputDTO toOutputDTO(ObservacaoConclusao model);
}
