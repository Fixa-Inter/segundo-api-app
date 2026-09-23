package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findById(Long id);

    List<Usuario> findByEnderecoIdAndTipoAcesso(Long enderecoId, TipoAcesso tipoAcesso);

}
