package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.ObservacaoConclusao;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosObservacaoConclusaoQueryParam;
import java.util.List;
import java.util.List;
import java.util.Optional;

public interface ObservacaoConclusaoRepository {

    Long save(ObservacaoConclusao observacaoConclusao, List<String> fotos);

    ObservacaoConclusao update(ObservacaoConclusao observacaoConclusao);

    boolean existsByOrdemServicoId(Long ordemServicoId);

    Optional<ObservacaoConclusao> findById(Long id);

    List<ObservacaoConclusao> findAllByUnidadeId(
            Long unidadeId,
            FiltrosObservacaoConclusaoQueryParam filtros
    );

    List<ObservacaoConclusao> findAllByResponsavelId(
            Long responsavelId,
            FiltrosObservacaoConclusaoQueryParam filtros
    );

    void delete(Long id);

    void deleteByOrdemServicoId(Long ordemServicoId);
}
