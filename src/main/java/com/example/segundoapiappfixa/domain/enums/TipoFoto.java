package com.example.segundoapiappfixa.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoFoto {

    PROBLEMA(),
    USUARIO(),
    OBSERVACAO_CONCLUSAO(),
    ORDEM_SERVICO(),
    OCORRENCIA()

}
