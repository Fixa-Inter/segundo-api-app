package com.example.segundoapiappfixa.application.usecase.ObservacaoConclusao;

import com.example.segundoapiappfixa.adapters.dto.input.ObservacaoConclusao.ObservacaoConclusaoCriarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.StatusOrdemServico;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.repository.FotoRepository;
import com.example.segundoapiappfixa.domain.repository.ObservacaoConclusaoRepository;
import com.example.segundoapiappfixa.domain.repository.OrdemServicoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@UseCase
@RequiredArgsConstructor
public class CadastrarObservacaoConclusao {

    private final ObservacaoConclusaoRepository observacaoConclusaoRepository;
    private final OrdemServicoRepository ordemServicoRepository;
    private final UsuarioRepository usuarioRepository;

    public ObservacaoConclusao cadastrar(
            ObservacaoConclusaoCriarInputDTO dto,
            Long usuarioId,
            boolean gestor
    ) {
        if (dto.observacao() != null && dto.observacao().isBlank())
            throw new RegraProblemaException("validation.observacaoConclusao.observacao.invalid");

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.notFound"));

        OrdemServico ordemServico = ordemServicoRepository.findById(dto.ordemServicoId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.ordemServico.notFound"));

        if (observacaoConclusaoRepository.existsByOrdemServicoId(ordemServico.getId()))
            throw new RegraProblemaException("exception.observacaoConclusao.alreadyExists");

        validarPermissao(usuario, ordemServico, gestor);

        ordemServico.setStatusOrdemServico(StatusOrdemServico.CONCLUIDA);
        ordemServicoRepository.save(ordemServico);

        Long observacaoId = observacaoConclusaoRepository.save(
                ordemServico.getId(),
                dto.dificuldade().getId(),
                dto.observacao(),
                dto.fotos()
        );

        return observacaoConclusaoRepository.findById(observacaoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.observacaoConclusao.notFound"));
    }

    private void validarPermissao(Usuario usuario, OrdemServico ordemServico, boolean gestor) {
        boolean mesmoUnidade = usuario.getUnidade().getId().equals(
                ordemServico.getProblema().getLocalUnidade().getUnidade().getId());

        if (gestor && mesmoUnidade) return;

        boolean tecnicoResponsavel = usuario.getId().equals(ordemServico.getUsuario().getId());

        if (!tecnicoResponsavel || !mesmoUnidade)
            throw new RegraProblemaException("exception.access.denied");
    }
}
