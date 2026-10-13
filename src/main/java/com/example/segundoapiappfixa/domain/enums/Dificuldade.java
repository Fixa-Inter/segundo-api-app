package com.example.segundoapiappfixa.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Dificuldade {
    FACIL("Fácil", 1),
    MEDIO("Médio", 2),
    DIFICIL("Difícil", 3);

    private final String nome;
    private final int id;

    public static Dificuldade fromNome(String nome) {
        for (Dificuldade dificuldade : values()) {
            if(dificuldade.getNome().equals(nome)) {
                return dificuldade;
            }
        }

        throw new IllegalArgumentException("Status inválido: " + nome);
    }

    public static Dificuldade fromId(int id) {
        for (Dificuldade dificuldade : values()) {
            if(dificuldade.getId() == id) {
                return dificuldade;
            }
        }

        throw new IllegalArgumentException("Status inválido: " + id);
    }

}
