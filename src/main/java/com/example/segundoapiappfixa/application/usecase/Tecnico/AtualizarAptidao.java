package com.example.segundoapiappfixa.application.usecase.Tecnico;

import com.example.segundoapiappfixa.adapters.dto.input.Aptidao.AptidaoAtualizarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.domain.model.Aptidao;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.AptidaoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class AtualizarAptidao {

    private final AptidaoRepository aptidaoRepository;
    private final UsuarioRepository usuarioRepository;

    public Aptidao atualizar(AptidaoAtualizarInputDTO dto, Long usuarioId) {
        Aptidao aptidao = aptidaoRepository.findById(dto.id())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.aptidao.notFound"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        if (!aptidao.getUsuario().getEndereco().getId().equals(usuario.getEndereco().getId())) {
            throw new RegraProblemaException("exception.access.denied");
        }

        if (dto.nota() != null) aptidao.setNota(dto.nota());
        if (dto.estaAtivo() != null) aptidao.setEstaAtivo(dto.estaAtivo());

        return aptidaoRepository.save(aptidao);
    }
}
