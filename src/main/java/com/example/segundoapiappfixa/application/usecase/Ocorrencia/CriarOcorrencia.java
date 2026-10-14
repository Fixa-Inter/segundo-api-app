package com.example.segundoapiappfixa.application.usecase.Ocorrencia;

import com.example.segundoapiappfixa.adapters.dto.input.Ocorrencia.OcorrenciaCadastrarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.enums.Prioridade;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.repository.CategoriaEquipamentoRepository;
import com.example.segundoapiappfixa.domain.repository.LocalUnidadeRepository;
import com.example.segundoapiappfixa.domain.repository.OcorrenciaRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.domain.repository.FotoRepository;
import com.example.segundoapiappfixa.domain.enums.TipoFoto;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class CriarOcorrencia {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LocalUnidadeRepository localUnidadeRepository;
    private final CategoriaEquipamentoRepository categoriaEquipamentoRepository;
    private final FotoRepository fotoRepository;

    public Ocorrencia cadastrar(OcorrenciaCadastrarInputDTO dto, Long usuarioId) {

        if (dto == null) {
            throw new RegraProblemaException("exception.ocorrencia.required");
        }

        LocalUnidade localUnidade = localUnidadeRepository.findById(dto.localUnidadeId())
                .orElseThrow(() -> new RegraProblemaException("exception.local.required"));

        CategoriaEquipamento categoriaEquipamento = categoriaEquipamentoRepository.findById(dto.categoriaEquipamentoId())
                .orElseThrow(() -> new RegraProblemaException("exception.categoriaEquipamento.required"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RegraProblemaException("exception.usuario.required"));

        Ocorrencia ocorrencia = new Ocorrencia(
                null,
                usuario,
                localUnidade,
                categoriaEquipamento,
                dto.categoriaProblemaId(),
                dto.prioridade(),
                dto.titulo(),
                dto.descricaoOcorrencia(),
                dto.descricaoLocal(),
                LocalDateTime.now(),
                true,
                List.of()
        );

        Ocorrencia persistida = ocorrenciaRepository.save(ocorrencia);
        List<Foto> fotos = new ArrayList<>();
        for (String url : dto.urlsFoto()) {
            Foto foto = new Foto(
                    null,
                    null,
                    persistida,
                    null,
                    null,
                    usuario,
                    url,
                    true,
                    LocalDateTime.now()
            );

            fotos.add(fotoRepository.save(foto, TipoFoto.OCORRENCIA));
        }
        persistida.setFotos(fotos);
        return persistida;
    }
}
