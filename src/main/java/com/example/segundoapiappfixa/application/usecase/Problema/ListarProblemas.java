package com.example.segundoapiappfixa.application.usecase.Problema;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.LocalUnidade;
import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosProblemaQueryParam;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.LocalUnidadeRepository;
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
    private final LocalUnidadeRepository localUnidadeRepository;
    private final ProblemaRepository problemaRepository;

    public List<Problema> listarMinhas(Long usuarioId, FiltrosProblemaQueryParam filtros) {
        List<Problema> problemas = problemaRepository.findAllByUsuarioId(usuarioId, filtros);

        if (problemas.isEmpty()) throw new com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException("exception.problema.notFound");

        return problemas;
    }

    public List<Problema> listar(Long usuarioId, FiltrosProblemaQueryParam filtros) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        if (usuario == null || usuario.getUnidade() == null || usuario.getUnidade().getId() == null) {
            throw new IllegalArgumentException("exception.request.invalid");
        }

        List<Long> localUnidadeIds = localUnidadeRepository
                .findAllByUnidadeId(usuario.getUnidade().getId())
                .stream()
                .map(LocalUnidade::getId)
                .toList();

        List<Problema> problemas = problemaRepository.findAllByLocalUnidadeIds(localUnidadeIds, filtros);

        if (problemas.isEmpty()) throw new com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException("exception.problema.notFound");

        return problemas;
    }

    public Problema listar(Long id, Long usuarioId, boolean isGestor) {

        Problema problema = problemaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.problema.notFound"));

        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        if (usuario == null || problema == null) {
            throw new RegraProblemaException("exception.unidade.required");
        }

        boolean mesmoUnidade = usuario.getUnidade().getId()
                .equals(problema.getLocalUnidade().getUnidade().getId());

        boolean donoDoProblema = problema.getUsuario() != null
                && usuarioId.equals(problema.getUsuario().getId());

        if (!mesmoUnidade || (!donoDoProblema && !isGestor)) {
            throw new RegraProblemaException("exception.access.denied");
        }

        return problema;
    }

}
