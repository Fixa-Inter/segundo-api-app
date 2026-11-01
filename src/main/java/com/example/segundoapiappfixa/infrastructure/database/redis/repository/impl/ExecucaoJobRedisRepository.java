package com.example.segundoapiappfixa.infrastructure.database.redis.repository.impl;

import com.example.segundoapiappfixa.adapters.mapper.ExecucaoJobMapper;
import com.example.segundoapiappfixa.domain.model.ExecucaoJob;
import com.example.segundoapiappfixa.domain.repository.ExecucaoJobRepository;
import com.example.segundoapiappfixa.infrastructure.database.redis.repository.JpaExecucaoJobRepository;
import com.example.segundoapiappfixa.infrastructure.database.redis.entity.ExecucaoJobEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ExecucaoJobRedisRepository implements ExecucaoJobRepository {
    private static final Duration LOCK_TTL = Duration.ofSeconds(ExecucaoJobEntity.TTL_SECONDS);

    private final JpaExecucaoJobRepository repository;
    private final ExecucaoJobMapper mapper;
    private final StringRedisTemplate redis;

    @Override
    public boolean iniciarSeAusente(ExecucaoJob execucao) {
        String lockKey = lockKey(execucao.getId());
        Boolean adquirido = redis.opsForValue().setIfAbsent(lockKey, "1", LOCK_TTL);
        if (!Boolean.TRUE.equals(adquirido)) return false;

        try {
            repository.save(mapper.toEntity(execucao));
            return true;
        } catch (RuntimeException exception) {
            redis.delete(lockKey);
            throw exception;
        }
    }

    @Override
    public Optional<ExecucaoJob> findById(String id) {
        return repository.findById(id).map(mapper::toModel);
    }

    @Override
    public void atualizar(ExecucaoJob execucao) {
        repository.save(mapper.toEntity(execucao));
    }

    private String lockKey(String id) {
        return "fixa:job-lock:" + id;
    }
}
