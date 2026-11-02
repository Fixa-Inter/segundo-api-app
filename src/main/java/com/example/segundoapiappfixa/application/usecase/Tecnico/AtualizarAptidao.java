package com.example.segundoapiappfixa.application.usecase.Tecnico;

import com.example.segundoapiappfixa.application.security.Permissoes;
import com.example.segundoapiappfixa.domain.model.input.Aptidao.AptidaoAtualizarDados;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.domain.model.Aptidao;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.AptidaoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.application.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.application.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class AtualizarAptidao {

    private final AptidaoRepository aptidaoRepository;
    private final UsuarioRepository usuarioRepository;

    public Aptidao atualizar(AptidaoAtualizarDados dto, Long usuarioId, TipoAcesso permissao) {
        Permissoes.validarGestao(permissao);
        Aptidao aptidao = aptidaoRepository.findById(dto.id())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.aptidao.notFound"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        boolean administrador = Permissoes.administrador(permissao);
        boolean alvoPermitido = TipoAcesso.TECNICO.equals(aptidao.getUsuario().getTipoAcesso())
                || (administrador && TipoAcesso.GESTOR.equals(aptidao.getUsuario().getTipoAcesso()));

        if ((!TipoAcesso.GESTOR.equals(permissao) && !administrador) || !alvoPermitido) {
            throw new RegraProblemaException("exception.gestor.required");
        }

        if (!aptidao.getUsuario().getUnidade().getId().equals(usuario.getUnidade().getId())) {
            throw new RegraProblemaException("exception.access.denied");
        }

        if (dto.nota() != null) aptidao.setNota(dto.nota());
        if (dto.estaAtivo() != null) aptidao.setEstaAtivo(dto.estaAtivo());

        return aptidaoRepository.save(aptidao);
    }
}
