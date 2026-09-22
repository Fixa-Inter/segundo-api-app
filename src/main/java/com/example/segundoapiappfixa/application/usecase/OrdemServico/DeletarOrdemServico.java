package com.example.segundoapiappfixa.application.usecase.OrdemServico;

import com.example.segundoapiappfixa.adapters.dto.output.OrdemServico.OrdemServicoDetalhesOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Problema.ProblemaDetalhesOutputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.Foto;
import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.domain.repository.FotoRepository;
import com.example.segundoapiappfixa.domain.repository.OrdemServicoRepository;
import com.example.segundoapiappfixa.domain.repository.TarefaRepository;
import com.example.segundoapiappfixa.domain.repository.UsuarioRepository;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.infrastructure.exception.EntidadeNaoEncontradaException;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class DeletarOrdemServico {

    private final OrdemServicoRepository ordemServicoRepository;
    private final TarefaRepository tarefaRepository;
    private final FotoRepository fotoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public OrdemServicoDetalhesOutputDTO deletar(
            Long ordemServicoId,
            Boolean isGestor,
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

        Problema problema = ordemServico.getProblema();
        Long quantidadeTarefas = tarefaRepository.countByOrdemServicoId(ordemServico.getId());

        List<String> urlsFotos = fotoRepository
                .findAllByProblemaId(ordemServico.getProblema().getId())
                .stream()
                .map(Foto::getUrl)
                .toList();

        ProblemaDetalhesOutputDTO problemaDetalhesOutputDTO = new ProblemaDetalhesOutputDTO(
                problema.getTitulo(),
                problema.getDescricaoProblema(),
                problema.getLocalEndereco().getNome(),
                problema.getDescricaoLocal(),
                problema.getCategoriaEquipamento().getNome(),
                problema.getStatus().getNome(),
                urlsFotos
        );

        OrdemServicoDetalhesOutputDTO response = new OrdemServicoDetalhesOutputDTO(
                problemaDetalhesOutputDTO,
                ordemServico.getCategoriaProblema().getNome(),
                ordemServico.getPrioridade().getNome(),
                ordemServico.getStatusOrdemServico().getNome(),
                ordemServico.getDataPrevista().toLocalDate(),
                quantidadeTarefas.intValue()
        );

        ordemServicoRepository.delete(ordemServicoId);
        return response;
    }
}
