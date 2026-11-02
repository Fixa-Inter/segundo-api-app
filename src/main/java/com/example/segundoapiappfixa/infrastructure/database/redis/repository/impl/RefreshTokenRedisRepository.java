package com.example.segundoapiappfixa.infrastructure.database.redis.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.RefreshTokenMapper;
import com.example.segundoapiappfixa.domain.model.RefreshToken;
import com.example.segundoapiappfixa.domain.repository.RefreshTokenRepository;
import com.example.segundoapiappfixa.infrastructure.database.redis.entity.RefreshTokenEntity;
import com.example.segundoapiappfixa.infrastructure.database.redis.repository.JpaRefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRedisRepository implements RefreshTokenRepository {
    private final JpaRefreshTokenRepository repository;
    private final RefreshTokenMapper mapper;

    @Override
    public void salvar(RefreshToken token) {
        repository.save(mapper.toEntity(token));
    }

    @Override
    public Optional<RefreshToken> buscarPorHash(String hash) {
        return repository.findById(hash).map(mapper::toModel);
    }

    @Override
    public void revogarFamilia(String familiaId) {
        List<RefreshTokenEntity> tokens = repository.findAllByFamiliaId(familiaId);

        for (RefreshTokenEntity token : tokens) {
            token.setAtivo(false);
            repository.save(token);
        }
    }

    @Override
    public void consumir(RefreshToken token) {
        token.setAtivo(false);
        repository.save(mapper.toEntity(token));
    }
}
