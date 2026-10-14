package com.example.segundoapiappfixa.application.usecase.ObservacaoConclusao;

import com.example.segundoapiappfixa.adapters.dto.input.ObservacaoConclusao.ObservacaoConclusaoAtualizarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.TipoFoto;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.domain.repository.*;
import com.example.segundoapiappfixa.infrastructure.exception.*;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class AtualizarObservacaoConclusao {
    private final ObservacaoConclusaoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final FotoRepository fotoRepository;

    public ObservacaoConclusao atualizar(ObservacaoConclusaoAtualizarInputDTO dto, Long usuarioId, boolean gestor) {
        if (dto.observacao() != null && dto.observacao().isBlank())
            throw new RegraProblemaException("validation.observacaoConclusao.observacao.invalid");

        ObservacaoConclusao atual = repository.findById(dto.id()).orElseThrow(() -> new EntidadeNaoEncontradaException("exception.observacaoConclusao.notFound"));

        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.notFound"));

        validarPermissao(atual.getOrdemServico(), usuario, gestor);

        if (dto.dificuldade() != null) atual.setDificuldade(dto.dificuldade());
        if (dto.observacao() != null) atual.setObservacao(dto.observacao());

        atual = repository.save(atual);

        if (dto.fotos() != null) {
            List<Foto> fotos = new ArrayList<>();

            for (String url : dto.fotos()) {
                Foto foto = new Foto(
                        null,
                        null,
                        null,
                        null,
                        atual,
                        usuario,
                        url,
                        true,
                        LocalDateTime.now()
                );

                fotos.add(fotoRepository.save(foto, TipoFoto.OBSERVACAO_CONCLUSAO));
            }

            atual.setFotos(fotos);
        }
        return atual;
    }

    public void validarPermissao(OrdemServico ordemServico, Usuario usuario, boolean gestor) {
        boolean mesmoUnidade = usuario.getUnidade().getId().equals(
                ordemServico.getProblema().getLocalUnidade().getUnidade().getId());

        if (gestor && mesmoUnidade) return;

        boolean tecnicoResponsavel = usuario.getId().equals(ordemServico.getUsuario().getId());

        if (!tecnicoResponsavel || !mesmoUnidade)
            throw new RegraProblemaException("exception.access.denied");
    }
}
