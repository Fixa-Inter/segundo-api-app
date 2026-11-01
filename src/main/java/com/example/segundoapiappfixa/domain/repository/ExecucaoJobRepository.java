package com.example.segundoapiappfixa.domain.repository;

import com.example.segundoapiappfixa.domain.model.ExecucaoJob;

import java.util.Optional;

public interface ExecucaoJobRepository {

    boolean iniciarSeAusente(ExecucaoJob execucao);

    Optional<ExecucaoJob> findById(String id);

    void atualizar(ExecucaoJob execucao);
}
