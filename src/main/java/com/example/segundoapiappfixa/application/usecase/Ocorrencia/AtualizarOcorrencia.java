package com.example.segundoapiappfixa.application.usecase.Ocorrencia;

import com.example.segundoapiappfixa.adapters.dto.input.Ocorrencia.OcorrenciaAtualizarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.enums.Prioridade;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.repository.OcorrenciaRepository;
import com.example.segundoapiappfixa.domain.repository.EquipamentoRepository;
import com.example.segundoapiappfixa.domain.repository.LocalEnderecoRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class AtualizarOcorrencia {
    private final OcorrenciaRepository ocorrenciaRepository;
    private final LocalEnderecoRepository localEnderecoRepository;
    private final EquipamentoRepository equipamentoRepository;

    public Ocorrencia atualizar(OcorrenciaAtualizarInputDTO dto, Long usuarioId) {

        Long id = dto.id();
        Ocorrencia atual = ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.ocorrencia.notFound"));

        if (atual.getUsuario() == null || !usuarioId.equals(atual.getUsuario().getId())) {
            throw new RegraProblemaException("exception.access.denied");
        }

        if (dto.localEnderecoId() != null) {
            atual.setLocalEndereco(localEnderecoRepository.findById(dto.localEnderecoId())
                    .orElseThrow(() -> new RegraProblemaException("exception.local.required")));
        }

        if (dto.equipamentoId() != null) {
            atual.setEquipamento(equipamentoRepository.findById(dto.equipamentoId())
                    .orElseThrow(() -> new RegraProblemaException("exception.equipamento.required")));
        }

        if (dto.categoriaProblemaId() != null) atual.setCategoriaProblema(
                CategoriaProblema.fromId(dto.categoriaProblemaId().intValue()));

        if (dto.prioridade() != null) atual.setPrioridade(
                Prioridade.fromId(dto.prioridade().intValue()));

        if (dto.titulo() != null) atual.setTitulo(dto.titulo());

        if (dto.descricaoOcorrencia() != null) atual.setDescricaoOcorrencia(dto.descricaoOcorrencia());

        if (dto.descricaoLocal() != null) atual.setDescricaoLocal(dto.descricaoLocal());

        return ocorrenciaRepository.update(id, atual);
    }
}
