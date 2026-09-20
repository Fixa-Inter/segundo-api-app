package com.example.segundoapiappfixa.adapters.mapper.dynamic;

import com.example.segundoapiappfixa.adapters.dto.output.Usuario.UsuarioOutputDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioDynamicMapper {

    public UsuarioOutputDTO mask(
            UsuarioOutputDTO dto,
            List<String> fields
    ) {
        return new UsuarioOutputDTO(
                fields.contains("id") ? dto.id() : null,
                fields.contains("nomeCompleto") ? dto.nomeCompleto() : null,
                fields.contains("email") ? dto.email() : null,
                fields.contains("tipoAcesso") ? dto.tipoAcesso() : null,
                fields.contains("cargo") ? dto.cargo() : null,
                fields.contains("dataNascimento") ? dto.dataNascimento() : null,
                fields.contains("cnpjEndereco") ? dto.cnpjEndereco() : null
        );
    }
}
