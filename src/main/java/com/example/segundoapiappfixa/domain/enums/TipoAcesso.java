package com.example.segundoapiappfixa.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoAcesso {
    ADMINISTRADOR("Administrador", 1),
    GESTOR("Gestor", 2),
    TECNICO("Técnico", 3),
    SOLICITANTE("Solicitante", 4);

    private final String nome;
    private final int id;

    public static TipoAcesso fromNome(String nome) {
        for (TipoAcesso tipoAcesso : values()) {
            if(tipoAcesso.getNome().equals(nome)) {
                return tipoAcesso;
            }
        }

        throw new IllegalArgumentException("Status inválido: " + nome);
    }

    public static TipoAcesso fromId(int id) {
        for (TipoAcesso tipoAcesso : values()) {
            if(tipoAcesso.getId() == id) {
                return tipoAcesso;
            }
        }

        throw new IllegalArgumentException("Status inválido: " + id);
    }
}
