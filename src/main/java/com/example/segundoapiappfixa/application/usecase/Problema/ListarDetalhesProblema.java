package com.example.segundoapiappfixa.application.usecase.Problema;

import com.example.segundoapiappfixa.adapters.dto.output.Problema.ProblemaDetalhesOutputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.Foto;
import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.domain.repository.FotoRepository;
import com.example.segundoapiappfixa.domain.repository.ProblemaRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListarDetalhesProblema {

    private final FotoRepository fotoRepository;
    private final ProblemaRepository problemaRepository;
    private final UsuarioRepository usuarioRepository;

    public ProblemaDetalhesOutputDTO listarDetalhesPeloId(Long id, Long usuarioId, boolean isGestor) {

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
