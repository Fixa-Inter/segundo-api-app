package com.example.segundoapiappfixa.application.usecase.MarcaEquipamento;

import com.example.segundoapiappfixa.adapters.dto.input.MarcaEquipamento.MarcaEquipamentoAtualizarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.MarcaEquipamento;
import com.example.segundoapiappfixa.domain.repository.MarcaEquipamentoRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;
import java.util.Locale;

@UseCase
@RequiredArgsConstructor
public class AtualizarMarcaEquipamento {
    private final MarcaEquipamentoRepository repository;
    public MarcaEquipamento atualizar(MarcaEquipamentoAtualizarInputDTO dto) {

        MarcaEquipamento marca = repository.findById(dto.id())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.marcaEquipamento.notFound"));

        if (dto.nome() != null && repository.existsByNomeIgnoreCaseAndIdNot(
                dto.nome().trim().toLowerCase(Locale.ROOT), dto.id()
        )) {
            throw new RegraProblemaException("exception.marcaEquipamento.duplicate");
        }

        if (dto.nome() != null) marca.setNome(dto.nome());
        if (dto.descricao() != null) marca.setDescricao(dto.descricao());
        if (dto.estaAtivo() != null) marca.setEstaAtivo(dto.estaAtivo());

        return repository.save(marca);
    }
}
