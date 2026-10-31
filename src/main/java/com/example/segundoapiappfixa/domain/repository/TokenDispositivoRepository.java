package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.TokenDispositivo;

import java.util.Optional;
import java.util.Set;

public interface TokenDispositivoRepository {
    void adicionar(TokenDispositivo tokenDispositivo);

    Optional<TokenDispositivo> findByToken(String token);

    void remover(String token);

    Set<String> listarPorUsuario(Long usuarioId);
}
