package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.infrastructure.database.entity.Usuario;

public interface UsuarioRepository {

    Usuario findByEmail(String email);

}
