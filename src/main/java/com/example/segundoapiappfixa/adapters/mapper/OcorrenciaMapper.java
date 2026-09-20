package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.adapters.dto.output.Ocorrencia.OcorrenciaOutputDTO;
import com.example.segundoapiappfixa.domain.model.Ocorrencia;
import com.example.segundoapiappfixa.infrastructure.database.entity.OcorrenciaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        UsuarioMapper.class,
        LocalEnderecoMapper.class,
        EquipamentoMapper.class
})
public interface OcorrenciaMapper {

    OcorrenciaEntity toEntity(Ocorrencia ocorrencia);
    Ocorrencia toModel(OcorrenciaEntity ocorrenciaEntity);

    @Mapping(source = "categoriaProblema.nome", target = "categoriaProblema")
    @Mapping(source = "prioridade.nome", target = "prioridade")
    @Mapping(source = "localEndereco.nome", target = "localEndereco")
    @Mapping(source = "equipamento.codigo", target = "equipamentoCodigo")
    @Mapping(source = "usuario.nomeCompleto", target = "nomeUsuario")
    @Mapping(source = "usuario.tipoAcesso.nome", target = "tipoAcesso")
    OcorrenciaOutputDTO toOutputDTO(Ocorrencia ocorrencia);
}
