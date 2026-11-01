package com.example.segundoapiappfixa.infrastructure.database.redis.repository;

import com.example.segundoapiappfixa.infrastructure.database.redis.entity.ExecucaoJobEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaExecucaoJobRepository extends CrudRepository<ExecucaoJobEntity, String> {
}
