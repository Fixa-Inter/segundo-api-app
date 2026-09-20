package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findById(Long id);

}
