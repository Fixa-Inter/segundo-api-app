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
import com.example.segundoapiappfixa.infrastructure.exception.OrdemServicoNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.ProblemaNaoEncontradoException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListarOrdensServico {

    private final UsuarioRepository usuarioRepository;
    private final LocalEnderecoRepository localEnderecoRepository;
    private final ProblemaRepository problemaRepository;
    private final OrdemServicoRepository ordemServicoRepository;

    public List<OrdemServico> listarOrdensServico(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId);

        if (usuario == null || usuario.getEndereco() == null || usuario.getEndereco().getId() == null) {
            throw new IllegalArgumentException();
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

        if (ordensServico.isEmpty()) throw new ProblemaNaoEncontradoException();

        return ordensServico;
    }

    public List<OrdemServico> listarOrdensServicoPeloUsuario(Long usuarioId) {

        List<OrdemServico> ordensServico = ordemServicoRepository.findByUsuarioId(usuarioId);
        if (ordensServico.isEmpty()) throw new OrdemServicoNaoEncontradaException();

        return ordensServico;
    }

}
