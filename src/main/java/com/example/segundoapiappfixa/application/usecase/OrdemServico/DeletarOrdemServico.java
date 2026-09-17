package com.example.segundoapiappfixa.application.usecase.OrdemServico;

import com.example.segundoapiappfixa.adapters.dto.output.OrdemServico.OrdemServicoDetalhesOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Problema.ProblemaDetalhesOutputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.Foto;
import com.example.segundoapiappfixa.domain.model.OrdemServico;
import com.example.segundoapiappfixa.domain.model.Problema;
import com.example.segundoapiappfixa.domain.model.Tarefa;
import com.example.segundoapiappfixa.domain.repository.FotoRepository;
import com.example.segundoapiappfixa.domain.repository.OrdemServicoRepository;
import com.example.segundoapiappfixa.domain.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import jakarta.persistence.EntityNotFoundException;

@UseCase
@RequiredArgsConstructor
public class DeletarOrdemServico {

    private final OrdemServicoRepository ordemServicoRepository;
    private final TarefaRepository tarefaRepository;
    private final FotoRepository fotoRepository;

    @Transactional
    public OrdemServicoDetalhesOutputDTO deletarOrdemServico(Long id) {
        OrdemServico ordemServico = ordemServicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Ordem de serviço não encontrada: " + id
                ));

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

        ordemServicoRepository.delete(id);
        return response;
    }
}
