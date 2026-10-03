package com.example.segundoapiappfixa.application.usecase.OrdemServico;

import com.example.segundoapiappfixa.adapters.dto.input.OrdemServico.OrdemServicoCadastrarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.domain.model.StatusOrdemServico;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.domain.repository.OrdemServicoRepository;
import com.example.segundoapiappfixa.domain.repository.ProblemaRepository;
import com.example.segundoapiappfixa.domain.repository.StatusOrdemServicoRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@UseCase
@RequiredArgsConstructor
public class CadastrarOrdemServico {
    private final OrdemServicoRepository ordemServicoRepository;
    private final ProblemaRepository problemaRepository;
    private final UsuarioRepository usuarioRepository;
    private final StatusOrdemServicoRepository statusRepository;

    @Transactional
    public OrdemServico cadastrar(OrdemServicoCadastrarInputDTO input, Long usuarioAutenticadoId) {
        Usuario gestor = buscarUsuario(usuarioAutenticadoId);
        Usuario responsavel = buscarUsuario(input.usuarioId());

        Problema problema = problemaRepository.findById(input.problemaId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.problema.notFound"));

        if (
                !gestor.getEndereco().getId().equals(responsavel.getEndereco().getId())
                || !gestor.getEndereco().getId().equals(problema.getLocalEndereco().getEndereco().getId())
        ) {
            throw new RegraProblemaException("exception.access.denied");
        }

        StatusOrdemServico status = statusRepository.findById(input.statusOrdemServicoId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.statusOrdemServico.notFound"));

        OrdemServico ordemServico = new OrdemServico(
                null,
                problema,
                responsavel,
                status,
                input.categoriaProblema(),
                input.prioridade(),
                LocalDateTime.now(),
                input.dataPrevista().atStartOfDay()
        );

        return ordemServicoRepository.save(ordemServico);
    }

    private Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("exception.usuario.notFound"));
    }
}
