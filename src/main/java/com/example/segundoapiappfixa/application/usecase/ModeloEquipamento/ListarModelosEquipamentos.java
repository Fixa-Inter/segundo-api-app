package com.example.segundoapiappfixa.application.usecase.ModeloEquipamento;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.ModeloEquipamento;
import com.example.segundoapiappfixa.adapters.dto.query_params.FiltrosModeloEquipamentoQueryParam;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.ModeloEquipamentoRepository;
import com.example.segundoapiappfixa.domain.repository.EquipamentoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListarModelosEquipamentos {
    private final ModeloEquipamentoRepository repository;
    private final EquipamentoRepository equipamentoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<ModeloEquipamentoResumo> listar(Long usuarioId, FiltrosModeloEquipamentoQueryParam filtros) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        List<ModeloEquipamento> modelos = repository.findByUnidadeId(usuario.getUnidade().getId(), filtros);

        if (modelos.isEmpty()) throw new EntidadeNaoEncontradaException("exception.modeloEquipamento.notFound");

        return criarResumo(modelos);
    }

    public ModeloEquipamentoResumo listar(Long id, Long usuarioId) {

        ModeloEquipamento modelo = repository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.modeloEquipamento.notFound"));

        validarUnidade(modelo, usuarioId);

        return criarResumo(List.of(modelo)).getFirst();
    }

    private List<ModeloEquipamentoResumo> criarResumo(List<ModeloEquipamento> modelos) {
        List<Long> ids = modelos.stream().map(ModeloEquipamento::getId).toList();
        var quantidades = equipamentoRepository.countAtivosByModeloIds(ids);
        return modelos.stream()
                .map(modelo -> new ModeloEquipamentoResumo(
                        modelo,
                        quantidades.getOrDefault(modelo.getId(), 0L)))
                .toList();
    }

    private void validarUnidade(ModeloEquipamento modelo, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        if (!modelo.getCategoriaEquipamento().getUsuario().getUnidade().getId()
                .equals(usuario.getUnidade().getId())
        ) {
            throw new RegraProblemaException("exception.access.denied");
        }

    }
}
