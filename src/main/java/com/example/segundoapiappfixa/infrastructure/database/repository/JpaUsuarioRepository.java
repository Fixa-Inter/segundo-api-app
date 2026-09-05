package com.example.segundoapiappfixa.infrastructure.database.repository;

import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.database.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findUsuarioByEmail(String email);

}
