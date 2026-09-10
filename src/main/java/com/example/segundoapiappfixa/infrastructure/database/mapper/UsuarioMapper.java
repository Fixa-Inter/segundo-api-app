package com.example.segundoapiappfixa.infrastructure.database.mapper;

import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.infrastructure.database.entity.UsuarioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toModel(UsuarioEntity entity);
    UsuarioEntity toEntity(Usuario model);

}
