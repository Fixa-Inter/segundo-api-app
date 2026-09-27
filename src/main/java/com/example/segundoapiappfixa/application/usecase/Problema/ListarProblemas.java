package com.example.segundoapiappfixa.application.usecase.Problema;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.LocalEndereco;
import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosProblemaQueryParam;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.LocalEnderecoRepository;
import com.example.segundoapiappfixa.domain.repository.ProblemaRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListarProblemas {

    private final UsuarioRepository usuarioRepository;
    private final LocalEnderecoRepository localEnderecoRepository;
    private final ProblemaRepository problemaRepository;

    public List<Problema> listarMinhas(Long usuarioId, FiltrosProblemaQueryParam filtros) {
        List<Problema> problemas = problemaRepository.findAllByUsuarioId(usuarioId, filtros);

        if (problemas.isEmpty()) throw new com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException("exception.problema.notFound");

        return problemas;
    }

    public List<Problema> listar(Long usuarioId, FiltrosProblemaQueryParam filtros) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        if (usuario == null || usuario.getEndereco() == null || usuario.getEndereco().getId() == null) {
            throw new IllegalArgumentException("exception.request.invalid");
        }

        List<Long> localEnderecoIds = localEnderecoRepository
                .findAllByEnderecoId(usuario.getEndereco().getId())
                .stream()
                .map(LocalEndereco::getId)
                .toList();

        List<Problema> problemas = problemaRepository.findAllByLocalEnderecoIds(localEnderecoIds, filtros);

        if (problemas.isEmpty()) throw new com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException("exception.problema.notFound");

        return problemas;
    }

    public Problema listar(Long id, Long usuarioId, boolean isGestor) {

        Problema problema = problemaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.problema.notFound"));

        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        if (usuario == null || problema == null) {
            throw new RegraProblemaException("exception.endereco.required");
        }

        boolean mesmoEndereco = usuario.getEndereco().getId()
                .equals(problema.getLocalEndereco().getEndereco().getId());

        boolean donoDoProblema = problema.getUsuario() != null
                && usuarioId.equals(problema.getUsuario().getId());

        if (!mesmoEndereco || (!donoDoProblema && !isGestor)) {
            throw new RegraProblemaException("exception.access.denied");
        }

        return problema;
    }

}
