package com.example.segundoapiappfixa.infrastructure.database.redis.repository;

import com.example.segundoapiappfixa.domain.model.RecuperarSenhaCodigo;
import com.example.segundoapiappfixa.infrastructure.database.redis.entity.RecuperarSenhaCodigoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaRecuperarSenhaCodigoRepository extends CrudRepository<RecuperarSenhaCodigoEntity, Long> {
    RecuperarSenhaCodigoEntity findByUsuarioIdAndEstaAtivo(Long usuarioId, boolean estaAtivo);

    List<RecuperarSenhaCodigoEntity> findAllByUsuarioId(Long usuarioId);
}
