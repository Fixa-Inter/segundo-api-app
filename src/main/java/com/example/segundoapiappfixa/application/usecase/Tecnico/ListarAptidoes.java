package com.example.segundoapiappfixa.application.usecase.Tecnico;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.domain.model.Aptidao;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.AptidaoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListarAptidoes {

    private final AptidaoRepository aptidaoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<Aptidao> listar(Long tecnicoId, Long usuarioId) {

        Usuario tecnico = usuarioRepository.findById(tecnicoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        if (!TipoAcesso.TECNICO.equals(tecnico.getTipoAcesso())) {
            throw new RegraProblemaException("exception.tecnico.required");
        }

        if (!TipoAcesso.GESTOR.equals(usuario.getTipoAcesso())) {
            throw new RegraProblemaException("exception.gestor.required");
        }

        if (!tecnico.getEndereco().getId().equals(usuario.getEndereco().getId())) {
            throw new RegraProblemaException("exception.access.denied");
        }

        List<Aptidao> competencias = aptidaoRepository.findByUsuarioId(tecnicoId);

        if (competencias.isEmpty()) {
            throw new EntidadeNaoEncontradaException("exception.aptidao.notFound");
        }

        return competencias;
    }
}
