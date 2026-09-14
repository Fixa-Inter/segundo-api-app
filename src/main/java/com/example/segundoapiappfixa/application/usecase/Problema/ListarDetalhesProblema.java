package com.example.segundoapiappfixa.application.usecase.Problema;

import com.example.segundoapiappfixa.adapters.dto.output.ProblemaDetalhesOutputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.Foto;
import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.domain.repository.FotoRepository;
import com.example.segundoapiappfixa.domain.repository.ProblemaRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListarDetalhesProblema {

    private final FotoRepository fotoRepository;
    private final ProblemaRepository problemaRepository;

    public ProblemaDetalhesOutputDTO listarDetalhesPeloId(Long id) {

        Problema problema = problemaRepository.findById(id)
                .orElseThrow(com.example.segundoapiappfixa.infrastructure.exception.ProblemaNaoEncontradoException::new);

        List<String> urlFotos = fotoRepository
                .findAllByProblemaId(id)
                .stream()
                .map(Foto::getUrl)
                .toList();


        return new ProblemaDetalhesOutputDTO(
                problema.getTitulo(),
                problema.getDescricaoProblema(),
                problema.getLocalEndereco().getNome(),
                problema.getDescricaoLocal(),
                problema.getCategoriaEquipamento().getNome(),
                problema.getStatus().getNome(),
                urlFotos
        );
    }
}
