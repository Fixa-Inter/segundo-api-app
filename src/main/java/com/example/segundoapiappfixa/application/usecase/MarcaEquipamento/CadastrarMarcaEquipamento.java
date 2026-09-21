package com.example.segundoapiappfixa.application.usecase.MarcaEquipamento;

import com.example.segundoapiappfixa.adapters.dto.input.MarcaEquipamento.MarcaEquipamentoCadastrarInputDTO;
import com.example.segundoapiappfixa.application.annotation.UseCase;
import com.example.segundoapiappfixa.domain.model.MarcaEquipamento;
import com.example.segundoapiappfixa.domain.repository.MarcaEquipamentoRepository;
import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.Locale;

@UseCase
@RequiredArgsConstructor
public class CadastrarMarcaEquipamento {

    private final MarcaEquipamentoRepository repository;

    public MarcaEquipamento cadastrar(MarcaEquipamentoCadastrarInputDTO dto) {

        if (repository.existsByNomeIgnoreCase(
                dto.nome().trim().toLowerCase(Locale.ROOT)
        )) {

            throw new RegraProblemaException("exception.marcaEquipamento.duplicate");
        }

        MarcaEquipamento marcaEquipamento = new MarcaEquipamento(
                null,
                dto.nome(),
                dto.descricao(),
                LocalDateTime.now(),
                true);

        return repository.save(marcaEquipamento);
    }
}
