package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.StatusOrdemServico;

import java.util.Optional;

public interface StatusOrdemServicoRepository {

    Optional<StatusOrdemServico> findById(Long id);

}
