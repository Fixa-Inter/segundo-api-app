package com.example.segundoapiappfixa.application.usecase.OrdemServico;

import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.domain.repository.OrdemServicoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class DeletarOrdemServico {

    private final OrdemServicoRepository ordemServicoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public OrdemServico deletar(
            Long ordemServicoId,
            boolean isGestor,
            Long usuarioId
    ) {

        OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.ordemServico.notFound"));

        if (!isGestor) throw new RegraProblemaException("exception.gestor.required");

        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        if (
                usuario == null ||
                ordemServico == null ||
                !usuario.getEndereco().getId()
                        .equals(ordemServico.getProblema().getLocalEndereco().getEndereco().getId())

        ) {
            throw new RegraProblemaException("exception.access.denied");
        }

        ordemServicoRepository.delete(ordemServicoId);
        return ordemServico;
    }
}
