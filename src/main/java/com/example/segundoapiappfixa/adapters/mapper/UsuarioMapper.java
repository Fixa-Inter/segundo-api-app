package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.adapters.dto.output.Usuario.UsuarioOutputDTO;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.infrastructure.database.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toModel(UsuarioEntity entity);
    UsuarioEntity toEntity(Usuario model);

    @Mapping(source = "tipoAcesso.nome", target = "tipoAcesso")
    @Mapping(source = "endereco.cnpj", target = "cnpjEndereco")
    UsuarioOutputDTO toOutputDTO(Usuario usuario);

}
