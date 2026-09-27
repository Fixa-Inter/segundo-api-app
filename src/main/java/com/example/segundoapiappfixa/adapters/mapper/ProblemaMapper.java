package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.adapters.dto.output.Problema.ProblemaOutputDTO;
import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.infrastructure.database.entity.ProblemaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = FotoMapper.class)
public interface ProblemaMapper {

    @Mapping(target = "fotos", ignore = true)
    ProblemaEntity toEntity(Problema problema);
    Problema toModel(ProblemaEntity entity);

    @Mapping(source = "categoriaEquipamento.nome", target = "categoriaEquipamento")
    @Mapping(source = "localEndereco.nome", target = "localEndereco")
    @Mapping(source = "status.nome", target = "status")
    @Mapping(source = "usuario.nomeCompleto", target = "usuario")
    @Mapping(source = "usuario.tipoAcesso.nome", target = "tipoAcesso")
    @Mapping(source = "fotos", target = "fotos")
    ProblemaOutputDTO toOutputDTO(Problema problema);

}
