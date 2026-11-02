package com.example.segundoapiappfixa.domain.repository;


import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.domain.filter.FiltrosOrdemServico;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

public interface OrdemServicoRepository {
    List<OrdemServico> findAllByProblemaIds(
            List<Long> problemaIds,
            FiltrosOrdemServico filtros
    );

    Optional<OrdemServico> findById(Long ordemServicoId);

    boolean existsByProblemaId(Long problemaId);

    OrdemServico save(OrdemServico ordemServico);

    void delete(Long ordemServicoId);

    List<OrdemServico> findByUsuarioId(
            Long usuarioId,
            FiltrosOrdemServico filtros
    );

    List<OrdemServico> findVencidasNaoConcluidas(LocalDateTime inicioDoDiaAtual);

}
