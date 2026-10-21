package com.example.segundoapiappfixa.application.usecase.Problema;

import com.example.segundoapiappfixa.adapters.dto.input.Problema.ProblemaCriarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import com.example.segundoapiappfixa.domain.enums.TipoFoto;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@UseCase
@RequiredArgsConstructor
public class CriarProblema {

    private final ProblemaRepository problemaRepository;
    private final FotoRepository fotoRepository;
    private final LocalUnidadeRepository localUnidadeRepository;
    private final CategoriaEquipamentoRepository categoriaEquipamentoRepository;
    private final UsuarioRepository usuarioRepository;

    public Problema cadastrar(ProblemaCriarInputDTO dto, Long usuarioId) {
        LocalUnidade localUnidade = localUnidadeRepository.findById(dto.localUnidadeID())
                .orElseThrow(() -> new EntityNotFoundException("validation.local.required"));

        CategoriaEquipamento categoriaEquipamento = categoriaEquipamentoRepository.findById(dto.categoriaEquipamentoId())
                .orElseThrow(() -> new EntityNotFoundException("validation.categoria.required"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("validation.usuario.required"));

        // Cadastro do Problema
        Long problemaId = problemaRepository.save(
                usuarioId,
                dto.localUnidadeID(),
                dto.categoriaEquipamentoId(),
                dto.titulo(),
                dto.descricaoProblema(),
                dto.descricaoLocal(),
                dto.urlsFoto()
        );

        return problemaRepository.findById(problemaId)
                .orElseThrow(() -> new EntityNotFoundException("Problema cadastrado não foi encontrado."));
    }
}
