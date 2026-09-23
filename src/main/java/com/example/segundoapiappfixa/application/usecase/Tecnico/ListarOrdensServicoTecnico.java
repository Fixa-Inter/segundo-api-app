package com.example.segundoapiappfixa.application.usecase.Tecnico;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.OrdemServicoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListarOrdensServicoTecnico {

    private final OrdemServicoRepository ordemServicoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<OrdemServico> listar(Long tecnicoId, Long usuarioId) {
        Usuario tecnico = usuarioRepository.findById(tecnicoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.required"));

        if (!TipoAcesso.TECNICO.equals(tecnico.getTipoAcesso())) {
            throw new RegraProblemaException("exception.tecnico.required");
        }

        if (!tecnico.getEndereco().getId().equals(usuario.getEndereco().getId())) {
            throw new RegraProblemaException("exception.access.denied");
        }

        List<OrdemServico> ordensServico = ordemServicoRepository.findByUsuarioId(tecnicoId);
        if (ordensServico.isEmpty()) {
            throw new EntidadeNaoEncontradaException("exception.ordemServico.notFound");
        }
        return ordensServico;
    }
}
