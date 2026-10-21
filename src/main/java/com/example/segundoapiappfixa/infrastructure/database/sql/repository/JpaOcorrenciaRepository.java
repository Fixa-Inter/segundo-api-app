package com.example.segundoapiappfixa.infrastructure.database.sql.repository;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.OcorrenciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaOcorrenciaRepository extends JpaRepository<OcorrenciaEntity, Long>, JpaSpecificationExecutor<OcorrenciaEntity> {

    // Procedure de salvar no banco de dados
    @Procedure(
            procedureName = "cadastrar_ocorrencia",
            outputParameterName = "p_ocorrencia_id"
    )
    Long cadastrarOcorrencia(
            @Param("p_usuario_id")
            Long usuarioId,

            @Param("p_local_id")
            Long localUnidadeId,

            @Param("p_categoria_equipamento_id")
            Long categoriaEquipamentoId,

            @Param("p_categoria_problema")
            Integer categoriaProblema,

            @Param("p_prioridade")
            Integer prioridade,

            @Param("p_titulo")
            String titulo,

            @Param("p_descricao_ocorrencia")
            String descricaoOcorrencia,

            @Param("p_descricao_local")
            String descricaoLocal,

            @Param("p_fotos_urls")
            List<String> fotos
    );

}
