package com.example.segundoapiappfixa.infrastructure.database.repository;

import com.example.segundoapiappfixa.infrastructure.database.entity.RecuperarSenhaCodigoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRecuperarSenhaCodigoRepository extends JpaRepository<RecuperarSenhaCodigoEntity, Long> {
    RecuperarSenhaCodigoEntity findFirstByUsuarioIdAndEstaAtivoTrueOrderByIdDesc(Long usuarioId);
}
