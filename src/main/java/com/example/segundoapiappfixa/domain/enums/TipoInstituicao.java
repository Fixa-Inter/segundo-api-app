package com.example.segundoapiappfixa.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoInstituicao {
    ESCOLA("Escola", 1),
    FACULDADE("Faculdade", 2),
    EMPRESA("Empresa", 3),
    ORGAO_PUBLICO("Órgão Público", 4);

    private final String nome;
    private final int id;

    public static TipoInstituicao fromNome(String nome) {
        for (TipoInstituicao tipoInstituicao : values()) {
            if(tipoInstituicao.getNome().equals(nome)) {
                return tipoInstituicao;
            }
        }

        throw new IllegalArgumentException("Status inválido: " + nome);
    }

    public static TipoInstituicao fromId(int id) {
        for (TipoInstituicao tipoInstituicao : values()) {
            if(tipoInstituicao.getId() == id) {
                return tipoInstituicao;
            }
        }

        throw new IllegalArgumentException("Status inválido: " + id);
    }
}
