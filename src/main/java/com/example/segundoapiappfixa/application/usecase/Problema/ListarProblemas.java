package com.example.segundoapiappfixa.application.usecase.Problema;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.LocalEndereco;
import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.LocalEnderecoRepository;
import com.example.segundoapiappfixa.domain.repository.ProblemaRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListarProblemas {

    private final UsuarioRepository usuarioRepository;
    private final LocalEnderecoRepository localEnderecoRepository;
    private final ProblemaRepository problemaRepository;

    public List<Problema> listarProblemasPeloUsuario(Long usuarioId) {
        List<Problema> problemas = problemaRepository.findAllByUsuarioId(usuarioId);

        if (problemas.isEmpty()) throw new com.example.segundoapiappfixa.infrastructure.exception.ProblemaNaoEncontradoException();

        return problemas;
    }

    public List<Problema> listarTodosProblemas(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId);

        if (usuario == null || usuario.getEndereco() == null || usuario.getEndereco().getId() == null) {
            throw new IllegalArgumentException();
        }

        List<Long> localEnderecoIds = localEnderecoRepository
                .findAllByEnderecoId(usuario.getEndereco().getId())
                .stream()
                .map(LocalEndereco::getId)
                .toList();

        List<Problema> problemas = problemaRepository.findAllByLocalEnderecoIds(localEnderecoIds);

        if (problemas.isEmpty()) throw new com.example.segundoapiappfixa.infrastructure.exception.ProblemaNaoEncontradoException();

        return problemas;
    }

}
