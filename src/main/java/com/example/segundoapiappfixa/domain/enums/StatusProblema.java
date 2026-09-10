package com.example.segundoapiappfixa.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusProblema {
    PENDENTE("Pendente", 0),
    REPROVADO("Reprovado", 1),
    APROVADO("Aprovado", 2);

    private String nome;
    private int id;

    public static StatusProblema fromNome(String nome) {
        for (StatusProblema statusProblema : values()) {
            if(statusProblema.getNome().equals(nome)) {
                return statusProblema;
            }
        }

        throw new IllegalArgumentException("Status inválido: " + nome);
    }

    public static StatusProblema fromId(int id) {
        for (StatusProblema statusProblema : values()) {
            if(statusProblema.getId() == id) {
                return statusProblema;
            }
        }

        throw new IllegalArgumentException("Status inválido: " + id);
    }

}
