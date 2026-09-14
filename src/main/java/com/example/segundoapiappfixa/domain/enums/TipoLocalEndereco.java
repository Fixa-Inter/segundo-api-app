package com.example.segundoapiappfixa.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoLocalEndereco {
    SALA_AULA("Sala de Aula", 1),
    LABORATORIO("Laboratório", 2),
    DEPTO_ADMINISTRATIVO("Depto. Administrativo", 3),
    AUDITORIO("Auditório", 4),
    ALMOXAFIRADO("Almoxarifado", 5),
    AREA_COMUM("Área Comum", 6);

    private final String nome;
    private final int id;

    public static TipoLocalEndereco fromNome(String nome) {
        for (TipoLocalEndereco tipoLocalEndereco : values()) {
            if(tipoLocalEndereco.getNome().equals(nome)) {
                return tipoLocalEndereco;
            }
        }

        throw new IllegalArgumentException("Status inválido: " + nome);
    }

    public static TipoLocalEndereco fromId(int id) {
        for (TipoLocalEndereco tipoLocalEndereco : values()) {
            if(tipoLocalEndereco.getId() == id) {
                return tipoLocalEndereco;
            }
        }

        throw new IllegalArgumentException("Status inválido: " + id);
    }
}
