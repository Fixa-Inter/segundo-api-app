package com.example.segundoapiappfixa.infrastructure.database.repository;

import com.example.segundoapiappfixa.infrastructure.database.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    UsuarioEntity findUsuarioByEmail(String email);

}
