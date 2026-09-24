package com.example.segundoapiappfixa.application.usecase.OrdemServico;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.LocalEndereco;
import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.LocalEnderecoRepository;
import com.example.segundoapiappfixa.domain.repository.OrdemServicoRepository;
import com.example.segundoapiappfixa.domain.repository.ProblemaRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListarOrdensServico {

    private final UsuarioRepository usuarioRepository;
    private final LocalEnderecoRepository localEnderecoRepository;
    private final ProblemaRepository problemaRepository;
    private final OrdemServicoRepository ordemServicoRepository;

    public List<OrdemServico> listar(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        if (usuario == null || usuario.getEndereco() == null || usuario.getEndereco().getId() == null) {
            throw new IllegalArgumentException("exception.request.invalid");
        }

        List<Long> localEnderecoIds = localEnderecoRepository
                .findAllByEnderecoId(usuario.getEndereco().getId())
                .stream()
                .map(LocalEndereco::getId)
                .toList();

        List<Long> problemasIds = problemaRepository
                .findAllByLocalEnderecoIds(localEnderecoIds)
                .stream()
                .map(Problema::getId)
                .toList();

        List<OrdemServico> ordensServico = ordemServicoRepository.findAllByProblemaIds(problemasIds);

        if (ordensServico.isEmpty()) throw new EntidadeNaoEncontradaException("exception.ordemServico.notFound");

        return ordensServico;
    }

    public List<OrdemServico> listarMinhas(Long usuarioId) {

        List<OrdemServico> ordensServico = ordemServicoRepository.findByUsuarioId(usuarioId);
        if (ordensServico.isEmpty()) throw new EntidadeNaoEncontradaException("exception.ordemServico.notFound");

        return ordensServico;
    }

    public OrdemServico listar(Long ordemServicoId, Long usuarioId, boolean isGestor) {
        OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.ordemServico.notFound"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.notFound"));


        boolean mesmoEndereco = usuario.getEndereco().getId().equals(
                ordemServico.getProblema().getLocalEndereco().getEndereco().getId()
        );

        boolean tecnicoVinculado = ordemServico.getUsuario() != null
                && usuarioId.equals(ordemServico.getUsuario().getId());

        if (!mesmoEndereco || (!isGestor && !tecnicoVinculado)) {
            throw new com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException(
                    "exception.access.denied"
            );
        }

        return ordemServico;
    }

}
