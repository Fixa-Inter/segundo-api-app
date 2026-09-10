package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Usuario;

public interface UsuarioRepository {

    Usuario findByEmail(String email);

}
