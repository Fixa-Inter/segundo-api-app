package com.example.segundoapiappfixa.application.usecase.ObservacaoConclusao;

import com.example.segundoapiappfixa.adapters.dto.input.ObservacaoConclusao.ObservacaoConclusaoCriarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.repository.FotoRepository;
import com.example.segundoapiappfixa.domain.repository.ObservacaoConclusaoRepository;
import com.example.segundoapiappfixa.domain.repository.OrdemServicoRepository;
import com.example.segundoapiappfixa.domain.repository.StatusOrdemServicoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.domain.enums.TipoFoto;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class CadastrarObservacaoConclusao {

    private final ObservacaoConclusaoRepository observacaoConclusaoRepository;
    private final OrdemServicoRepository ordemServicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final FotoRepository fotoRepository;
    private final StatusOrdemServicoRepository statusRepository;

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

        StatusOrdemServico status = statusRepository.findById(dto.statusOrdemServicoId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.statusOrdemServico.notFound"));

        ordemServico.setStatusOrdemServico(status);
        ordemServicoRepository.save(ordemServico);

        ObservacaoConclusao observacaoConclusao = new ObservacaoConclusao(
                null,
                ordemServico,
                dto.dificuldade(),
                dto.observacao(),
                LocalDate.now(),
                List.of()
        );

        observacaoConclusao = observacaoConclusaoRepository.save(observacaoConclusao);
        List<Foto> fotos = new ArrayList<>();

        for (String url : dto.fotos()) {
            Foto foto = new Foto(
                    null,
                    null,
                    null,
                    null,
                    observacaoConclusao,
                    null,
                    url,
                    true,
                    LocalDateTime.now()
            );

            fotos.add(fotoRepository.save(foto, TipoFoto.OBSERVACAO_CONCLUSAO));
        }

        return observacaoConclusao;
    }

    private void validarPermissao(Usuario usuario, OrdemServico ordemServico, boolean gestor) {
        boolean mesmoEndereco = usuario.getEndereco().getId().equals(
                ordemServico.getProblema().getLocalEndereco().getEndereco().getId());

        if (gestor && mesmoEndereco) return;

        boolean tecnicoResponsavel = usuario.getId().equals(ordemServico.getUsuario().getId());

        if (!tecnicoResponsavel || !mesmoEndereco)
            throw new RegraProblemaException("exception.access.denied");
    }
}
