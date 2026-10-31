package com.example.segundoapiappfixa.infrastructure.database.redis.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.TokenDispositivoMapper;
import com.example.segundoapiappfixa.domain.model.TokenDispositivo;
import com.example.segundoapiappfixa.domain.repository.TokenDispositivoRepository;
import com.example.segundoapiappfixa.infrastructure.database.redis.repository.JpaTokenDispositivoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TokenDispositivoRedisRepository implements TokenDispositivoRepository {

    private final JpaTokenDispositivoRepository repository;
    private final TokenDispositivoMapper mapper;

    @Override
    public void adicionar(TokenDispositivo tokenDispositivo) {
        repository.save(mapper.toEntity(tokenDispositivo));
    }

    @Override
    public Optional<TokenDispositivo> findByToken(String token) {
        return repository.findById(token).map(mapper::toModel);
    }

    @Override
    public void remover(String token) {
        repository.deleteById(token);
    }

    @Override
    public Set<String> listarPorUsuario(Long usuarioId) {
        return repository.findAllByUsuarioId(usuarioId).stream()
                .map(mapper::toModel)
                .map(TokenDispositivo::getToken)
                .collect(Collectors.toSet());
    }
}
