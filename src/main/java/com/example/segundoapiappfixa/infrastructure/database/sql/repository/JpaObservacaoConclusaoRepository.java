package com.example.segundoapiappfixa.infrastructure.database.sql.repository;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.ObservacaoConclusaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaObservacaoConclusaoRepository extends JpaRepository<ObservacaoConclusaoEntity, Long>, JpaSpecificationExecutor<ObservacaoConclusaoEntity> {

    boolean existsByOrdemServico_Id(Long ordemServicoId);

    // Procedure de salvar no banco de dados
    @Procedure(
            procedureName = "cadastrar_observacao_conclusao",
            outputParameterName = "p_observacao_conclusao_id"
    )
    Long cadastrarObservacaoConclusao(
            @Param("p_ordem_servico_id")
            Long ordemServicoId,

            @Param("p_dificuldade")
            Integer dificuldade,

            @Param("p_observacao")
            String observacao,

            @Param("p_fotos_urls")
            List<String> fotos
    );
}
