package com.example.segundoapiappfixa.auth.service;

import com.example.segundoapiappfixa.auth.dto.CadastroNomeOutputDTO;
import com.example.segundoapiappfixa.domain.model.Unidade;
import com.example.segundoapiappfixa.domain.repository.UnidadeRepository;
import com.example.segundoapiappfixa.domain.repository.InstituicaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarOpcoesCadastro {

    private final InstituicaoRepository instituicaoRepository;
    private final UnidadeRepository unidadeRepository;

    @Transactional(readOnly = true)
    public List<CadastroNomeOutputDTO> listarInstituicoes() {
        return instituicaoRepository.findAll()
                .stream()
                .map(instituicao -> new CadastroNomeOutputDTO(instituicao.getId(), instituicao.getNome()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CadastroNomeOutputDTO> listarUnidades(Long instituicaoId) {
        return unidadeRepository
                .findAllByInstituicaoId(instituicaoId)
                .stream()
                .map(this::toOutput)
                .toList();
    }

    private CadastroNomeOutputDTO toOutput(Unidade unidade) {
        return new CadastroNomeOutputDTO(unidade.getId(), unidade.getNome());
    }
}
