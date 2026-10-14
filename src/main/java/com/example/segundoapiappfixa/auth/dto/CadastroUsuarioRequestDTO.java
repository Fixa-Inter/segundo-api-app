package com.example.segundoapiappfixa.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record CadastroUsuarioRequestDTO(
        @NotBlank
        String nomeCompleto,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Pattern(regexp = ".{4,}")
        String senha,

        @NotBlank
        @Pattern(regexp = "\\d{11}")
        String telefone,

        @NotNull
        Long enderecoId,

        @NotBlank
        String cargo,

        @NotNull
        @Past
        LocalDate dataNascimento
) {
}
