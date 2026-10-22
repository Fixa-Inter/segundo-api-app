package com.example.segundoapiappfixa.application.usecase.Problema;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.repository.*;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class CriarProblema {

    private final ProblemaRepository problemaRepository;
    private final LocalUnidadeRepository localUnidadeRepository;
    private final CategoriaEquipamentoRepository categoriaEquipamentoRepository;
    private final UsuarioRepository usuarioRepository;

    public Problema cadastrar(Problema problema, List<String> fotos, Long usuarioId) {
        if (problema == null) {
            throw new EntidadeNaoEncontradaException("validation.problema.required");
        }

        Long localUnidadeId = problema.getLocalUnidade() == null
                ? null : problema.getLocalUnidade().getId();

        Long categoriaEquipamentoId = problema.getCategoriaEquipamento() == null
                ? null : problema.getCategoriaEquipamento().getId();

        LocalUnidade localUnidade = localUnidadeRepository.findById(localUnidadeId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("validation.local.required"));

        CategoriaEquipamento categoriaEquipamento = categoriaEquipamentoRepository.findById(categoriaEquipamentoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("validation.categoria.required"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("validation.usuario.required"));

        problema.setUsuario(usuario);
        problema.setLocalUnidade(localUnidade);
        problema.setCategoriaEquipamento(categoriaEquipamento);

        Long problemaId = problemaRepository.save(problema, fotos);

        return problemaRepository.findById(problemaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Problema cadastrado não foi encontrado."));
    }
}
