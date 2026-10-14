package com.example.segundoapiappfixa.auth.service;

import com.example.segundoapiappfixa.auth.dto.CadastroNomeOutputDTO;
import com.example.segundoapiappfixa.domain.model.Endereco;
import com.example.segundoapiappfixa.domain.repository.EnderecoRepository;
import com.example.segundoapiappfixa.domain.repository.InstituicaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarOpcoesCadastro {

    private final InstituicaoRepository instituicaoRepository;
    private final EnderecoRepository enderecoRepository;

    @Transactional(readOnly = true)
    public List<CadastroNomeOutputDTO> listarInstituicoes() {
        return instituicaoRepository.findAll()
                .stream()
                .map(instituicao -> new CadastroNomeOutputDTO(instituicao.getId(), instituicao.getNome()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CadastroNomeOutputDTO> listarEnderecos(Long instituicaoId) {
        return enderecoRepository
                .findAllByInstituicaoId(instituicaoId)
                .stream()
                .map(this::toOutput)
                .toList();
    }

    private CadastroNomeOutputDTO toOutput(Endereco endereco) {
        String nome = String.format(
                "%s, %s - %s, %s - %s",
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado()
        );

        return new CadastroNomeOutputDTO(endereco.getId(), nome);
    }
}
