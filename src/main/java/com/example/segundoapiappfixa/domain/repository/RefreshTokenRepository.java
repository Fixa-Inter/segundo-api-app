package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {
    void salvar(RefreshToken token);

    Optional<RefreshToken> buscarPorHash(String hash);

    void revogarFamilia(String familiaId);

    void consumir(RefreshToken token);
}
