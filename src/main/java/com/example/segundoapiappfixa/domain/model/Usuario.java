package com.example.segundoapiappfixa.domain.model;

import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private Integer id;
    private String nomeCompleto;
    private String email;
    private TipoAcesso tipoAcesso;
    private String senhaHash;
    private LocalDate dataNascimento;
    private LocalDateTime dataCriacao;
    private Boolean estaAtivo;
}
