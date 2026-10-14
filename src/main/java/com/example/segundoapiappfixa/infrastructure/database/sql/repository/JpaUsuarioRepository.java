package com.example.segundoapiappfixa.infrastructure.database.sql.repository;

import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.UsuarioEntity;
import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.enums.StatusOrdemServico;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaUsuarioRepository extends JpaRepository<UsuarioEntity, Long>, JpaSpecificationExecutor<UsuarioEntity> {

    UsuarioEntity findUsuarioByEmail(String email);

    @Query("""
            SELECT
                u
            FROM
                Usuario u
            JOIN
                Aptidao a
                ON a.usuario = u
                AND a.categoriaProblema = :categoria
                AND a.estaAtivo = true
            LEFT JOIN
                Tarefa t
                ON t.ordemServico.usuario = u
                AND t.statusOrdemServico <> :concluida
            WHERE
                u.unidade.id = :unidadeId
                AND u.tipoAcesso = :tecnico
                AND u.estaAtivo = true
            GROUP BY
                u, a.nota
            ORDER BY
                a.nota DESC,
                COUNT(t.id) ASC
            """)
    List<UsuarioEntity> findTecnicosRecomendados(
            @Param("unidadeId") Long unidadeId,
            @Param("categoria") CategoriaProblema categoria,
            @Param("tecnico") TipoAcesso tecnico,
            @Param("concluida") StatusOrdemServico concluida
    );

}
