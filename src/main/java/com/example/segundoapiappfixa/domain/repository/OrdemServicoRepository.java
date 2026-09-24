package com.example.segundoapiappfixa.domain.repository;


import com.example.segundoapiappfixa.domain.model.OrdemServico;

import java.util.List;
import java.util.Optional;

public interface OrdemServicoRepository {

    List<OrdemServico> findAllByProblemaIds(List<Long> problemaIds);

    Optional<OrdemServico> findById(Long id);

    OrdemServico save(OrdemServico ordemServico);

    void delete(Long id);

    List<OrdemServico> findByUsuarioId(Long usuarioId);
}
