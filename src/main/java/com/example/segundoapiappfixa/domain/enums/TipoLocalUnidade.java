package com.example.segundoapiappfixa.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoLocalUnidade {
    SALA_AULA("Sala de Aula", 1),
    LABORATORIO("Laboratório", 2),
    DEPTO_ADMINISTRATIVO("Depto. Administrativo", 3),
    AUDITORIO("Auditório", 4),
    ALMOXAFIRADO("Almoxarifado", 5),
    AREA_COMUM("Área Comum", 6);

    private final String nome;
    private final int id;

    public static TipoLocalUnidade fromNome(String nome) {
        for (TipoLocalUnidade tipoLocalUnidade : values()) {
            if(tipoLocalUnidade.getNome().equals(nome)) {
                return tipoLocalUnidade;
            }
        }

        throw new IllegalArgumentException("Status inválido: " + nome);
    }

    public static TipoLocalUnidade fromId(int id) {
        for (TipoLocalUnidade tipoLocalUnidade : values()) {
            if(tipoLocalUnidade.getId() == id) {
                return tipoLocalUnidade;
            }
        }

        throw new IllegalArgumentException("Status inválido: " + id);
    }
}
