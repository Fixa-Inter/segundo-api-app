package com.example.segundoapiappfixa.infrastructure.database.repository;

import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.infrastructure.database.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    UsuarioEntity findUsuarioByEmail(String email);

    UsuarioEntity findUsuarioEntitiesById(Long id);

    List<UsuarioEntity> findUsuarioEntitiesByEndereco_IdAndTipoAcesso(
            Long enderecoId,
            TipoAcesso tipoAcesso
    );

}
