package com.example.segundoapiappfixa.infrastructure.database.sql.repository;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.AptidaoEntity;
import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaAptidaoRepository extends JpaRepository<AptidaoEntity, Long> {

    List<AptidaoEntity> findAllByUsuario_IdOrderByNotaDesc(Long usuarioId);

    List<AptidaoEntity> findAllByUsuario_IdInAndEstaAtivoTrueOrderByNotaDesc(List<Long> usuarioIds);

    List<AptidaoEntity> findAllByUsuario_IdInAndCategoriaProblemaAndEstaAtivoTrueOrderByNotaDesc(
            List<Long> usuarioIds,
            CategoriaProblema categoriaProblema
    );

}
