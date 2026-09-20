package com.example.segundoapiappfixa.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Prioridade {
    ALTA("Alta", 0),
    MEDIA("Média", 1),
    BAIXA("Baixa", 2);

    private String nome;
    private int id;

    public static Prioridade fromNome(String nome) {
        for (Prioridade prioridade : values()) {
            if(prioridade.getNome().equals(nome)) {
                return prioridade;
            }
        }

        throw new IllegalArgumentException("Status inválido: " + nome);
    }

    public static Prioridade fromId(int id) {
        for (Prioridade prioridade : values()) {
            if(prioridade.getId() == id) {
                return prioridade;
            }
        }

        throw new IllegalArgumentException("Status inválido: " + id);
    }

}
