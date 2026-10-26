package com.example.segundoapiappfixa.infrastructure.database.sql.repository;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.TarefaEntity;
import com.example.segundoapiappfixa.domain.enums.StatusOrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface JpaTarefaRepository extends JpaRepository<TarefaEntity, Long>, JpaSpecificationExecutor<TarefaEntity> {

    void deleteByOrdemServico_Id(Long ordemServicoId);

    @Query("""
            SELECT t.ordemServico.usuario.id, COUNT(t.id)
            FROM Tarefa t
            WHERE t.ordemServico.usuario.id IN :tecnicoIds
              AND t.statusOrdemServico = :status
            GROUP BY t.ordemServico.usuario.id
            """)
    List<Object[]> countByTecnicoIdsAndStatus(
            @Param("tecnicoIds") Collection<Long> tecnicoIds,
            @Param("status") StatusOrdemServico status
    );
}
