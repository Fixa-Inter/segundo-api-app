package com.example.segundoapiappfixa.application.usecase.Problema;

import com.example.segundoapiappfixa.adapters.dto.input.ProblemaCriarInputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.ProblemaDetalhesOutputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import com.example.segundoapiappfixa.domain.model.*;
import com.example.segundoapiappfixa.domain.repository.*;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@UseCase
@RequiredArgsConstructor
public class CriarProblema {

    private final ProblemaRepository problemaRepository;
    private final FotoRepository fotoRepository;
    private final LocalEnderecoRepository localEnderecoRepository;
    private final CategoriaEquipamentoRepository categoriaEquipamentoRepository;
    private final UsuarioRepository usuarioRepository;

    public ProblemaDetalhesOutputDTO criar(ProblemaCriarInputDTO dto, Long usuarioId) {
        LocalEndereco localEndereco = localEnderecoRepository.
                findById(dto.localEnderecoID());

        CategoriaEquipamento categoriaEquipamento = categoriaEquipamentoRepository.
                findById(dto.categoriaEquipamentoId());

        Usuario usuario = usuarioRepository.findById(usuarioId);

        // Cadastro do Problema
        Problema problema = new Problema(
                null,
                usuario,
                categoriaEquipamento,
                localEndereco,
                dto.titulo(),
                dto.descricaoProblema(),
                dto.descricaoLocal(),
                LocalDateTime.now(),
                StatusProblema.PENDENTE
        );

        Problema problemaPersistido = problemaRepository.save(problema);

        // Cadastro Imagens
        for (String url : dto.urlsFoto()) {
            Foto foto = new Foto(
                    null,
                    problemaPersistido,
                    null,
                    null,
                    null,
                    url,
                    true
            );

            fotoRepository.save(foto);
        }

        // Retorno do problema cadastrado
        return new ProblemaDetalhesOutputDTO(
                problema.getTitulo(),
                problema.getDescricaoProblema(),
                problema.getLocalEndereco().getNome(),
                problema.getDescricaoLocal(),
                problema.getCategoriaEquipamento().getNome(),
                problema.getStatus().getNome(),
                dto.urlsFoto()
        );
    }
}
