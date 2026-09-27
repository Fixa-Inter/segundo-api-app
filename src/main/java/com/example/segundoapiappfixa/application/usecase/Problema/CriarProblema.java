package com.example.segundoapiappfixa.application.usecase.Problema;

import com.example.segundoapiappfixa.adapters.dto.input.Problema.ProblemaCriarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.repository.*;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@UseCase
@RequiredArgsConstructor
public class CriarProblema {

    private final ProblemaRepository problemaRepository;
    private final FotoRepository fotoRepository;
    private final LocalEnderecoRepository localEnderecoRepository;
    private final CategoriaEquipamentoRepository categoriaEquipamentoRepository;
    private final UsuarioRepository usuarioRepository;

    public Problema cadastrar(ProblemaCriarInputDTO dto, Long usuarioId) {
        LocalEndereco localEndereco = localEnderecoRepository.findById(dto.localEnderecoID()).orElse(null);

        CategoriaEquipamento categoriaEquipamento = categoriaEquipamentoRepository.findById(dto.categoriaEquipamentoId()).orElse(null);

        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        // Cadastro do Problema
        Problema problema = new Problema(
                null,
                usuario,
                categoriaEquipamento,
                localEndereco,
                dto.titulo(),
                dto.descricaoProblema(),
                dto.descricaoLocal(),
                null,
                LocalDateTime.now(),
                StatusProblema.PENDENTE,
                List.of()
        );

        Problema problemaPersistido = problemaRepository.save(problema);
        List<Foto> fotos = new ArrayList<>();

        // Cadastro Imagens
        for (String url : dto.urlsFoto()) {
            Foto foto = new Foto(
                    null,
                    problemaPersistido,
                    null,
                    null,
                    null,
                    url,
                    true,
                    LocalDateTime.now()
            );

            fotos.add(fotoRepository.save(foto));
        }

        problemaPersistido.setFotos(fotos);

        return problemaPersistido;
    }
}
