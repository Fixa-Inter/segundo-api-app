package com.example.segundoapiappfixa.application.usecase.MarcaEquipamento;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.MarcaEquipamento;
import com.example.segundoapiappfixa.domain.repository.MarcaEquipamentoRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListarMarcasEquipamento {

    private final MarcaEquipamentoRepository repository;

    public List<MarcaEquipamento> listar() {
        return repository.findAll();
    }

    public MarcaEquipamento listar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.marcaEquipamento.notFound"));
    }
}
