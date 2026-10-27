package com.example.segundoapiappfixa.application.usecase.Tecnico;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.model.Aptidao;
import com.example.segundoapiappfixa.domain.repository.AptidaoRepository;
import com.example.segundoapiappfixa.domain.repository.TarefaRepository;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosUsuarioQueryParam;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@UseCase
@RequiredArgsConstructor
public class ListarTecnicos {

    private final UsuarioRepository usuarioRepository;
    private final AptidaoRepository aptidaoRepository;
    private final TarefaRepository tarefaRepository;

    public List<TecnicoResumo> listar(
            Long usuarioId,
            FiltrosUsuarioQueryParam filtros,
            boolean incluirGestores
    ) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        List<Usuario> usuarios = usuarioRepository.findByUnidadeIdAndTipoAcesso(
                usuario.getUnidade().getId(),
                TipoAcesso.TECNICO,
                filtros
        );

        if (incluirGestores) {
            usuarios = new ArrayList<>(usuarios);
            usuarios.addAll(usuarioRepository.findByUnidadeIdAndTipoAcesso(
                    usuario.getUnidade().getId(),
                    TipoAcesso.GESTOR,
                    filtros
            ));
        }

        if (usuarios.isEmpty()) throw new EntidadeNaoEncontradaException("exception.usuario.required");

        return enriquecerComResumo(usuarios);
    }

    public List<TecnicoResumo> listarRecomendar(
            CategoriaProblema categoriaProblema,
            Long usuarioId
    ) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        List<Usuario> tecnicos = usuarioRepository.findTecnicosRecomendados(
                usuario.getUnidade().getId(),
                categoriaProblema
        );

        if (tecnicos.isEmpty()) {
            throw new EntidadeNaoEncontradaException("exception.usuario.required");
        }

        return enriquecerComResumo(tecnicos, categoriaProblema);
    }

    private List<TecnicoResumo> enriquecerComResumo(List<Usuario> tecnicos) {
        List<Long> ids = tecnicos.stream().map(Usuario::getId).toList();
        Map<Long, Aptidao> competencias = aptidaoRepository.findCompetenciasVencedorasByUsuarioIds(ids);
        return criarResumo(tecnicos, ids, competencias);
    }

    private List<TecnicoResumo> enriquecerComResumo(
            List<Usuario> tecnicos,
            CategoriaProblema categoriaProblema
    ) {
        List<Long> ids = tecnicos.stream().map(Usuario::getId).toList();
        Map<Long, Aptidao> competencias = aptidaoRepository.findCompetenciasVencedorasByUsuarioIds(
                ids,
                categoriaProblema
        );
        return criarResumo(tecnicos, ids, competencias);
    }

    private List<TecnicoResumo> criarResumo(
            List<Usuario> tecnicos,
            List<Long> ids,
            Map<Long, Aptidao> competencias
    ) {
        Map<Long, Long> tarefasEmAndamento = tarefaRepository.countEmAndamentoByTecnicoIds(ids);

        return tecnicos.stream()
                .map(tecnico -> new TecnicoResumo(
                        tecnico,
                        competencias.get(tecnico.getId()),
                        tarefasEmAndamento.getOrDefault(tecnico.getId(), 0L)
                ))
                .toList();
    }

}
