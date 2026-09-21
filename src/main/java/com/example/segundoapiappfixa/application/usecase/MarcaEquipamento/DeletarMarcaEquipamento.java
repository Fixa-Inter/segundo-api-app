package com.example.segundoapiappfixa.application.usecase.MarcaEquipamento;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.MarcaEquipamento;
import com.example.segundoapiappfixa.domain.repository.MarcaEquipamentoRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeletarMarcaEquipamento {

    private final MarcaEquipamentoRepository repository;

    public MarcaEquipamento deletar(Long id) {
        return repository.deleteById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.marcaEquipamento.notFound"));
    }
}
