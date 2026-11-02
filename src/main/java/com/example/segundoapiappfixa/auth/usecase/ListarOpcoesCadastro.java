package com.example.segundoapiappfixa.auth.usecase;

import com.example.segundoapiappfixa.domain.model.Instituicao;
import com.example.segundoapiappfixa.domain.model.Unidade;
import com.example.segundoapiappfixa.domain.repository.UnidadeRepository;
import com.example.segundoapiappfixa.domain.repository.InstituicaoRepository;
import lombok.RequiredArgsConstructor;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListarOpcoesCadastro {

    private final InstituicaoRepository instituicaoRepository;
    private final UnidadeRepository unidadeRepository;

    @Transactional(readOnly = true)
    public List<Instituicao> listarInstituicoes() {
        return instituicaoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Unidade> listarUnidades(Long instituicaoId) {
        return unidadeRepository.findAllByInstituicaoId(instituicaoId);
    }
}
