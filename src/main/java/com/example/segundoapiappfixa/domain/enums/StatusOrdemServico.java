package com.example.segundoapiappfixa.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusOrdemServico {
    NAO_INICIADA("Não Iniciada", 0),
    ATRASADA("Atrasada", 1),
    PAUSADA("Pausada", 2),
    EM_ANDAMENTO("Em andamento", 3),
    CONCLUIDA("Concluída", 4);

    private final String nome;
    private final int id;

    public static StatusOrdemServico fromNome(String nome) {
        for (StatusOrdemServico statusOrdemServico : values()) {
            if(statusOrdemServico.getNome().equals(nome)) {
                return statusOrdemServico;
            }
        }

        throw new IllegalArgumentException("Status inválido: " + nome);
    }

    public static StatusOrdemServico fromId(int id) {
        for (StatusOrdemServico statusOrdemServico : values()) {
            if(statusOrdemServico.getId() == id) {
                return statusOrdemServico;
            }
        }

        throw new IllegalArgumentException("Status inválido: " + id);
    }
}
