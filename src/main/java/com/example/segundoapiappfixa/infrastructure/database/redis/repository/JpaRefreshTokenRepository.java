package com.example.segundoapiappfixa.infrastructure.database.redis.repository;

import com.example.segundoapiappfixa.infrastructure.database.redis.entity.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaRefreshTokenRepository extends CrudRepository<RefreshTokenEntity, String> {
    List<RefreshTokenEntity> findAllByFamiliaId(String familiaId);
}
