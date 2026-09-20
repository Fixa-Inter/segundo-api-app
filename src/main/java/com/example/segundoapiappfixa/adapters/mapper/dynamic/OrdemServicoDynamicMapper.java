package com.example.segundoapiappfixa.adapters.mapper.dynamic;

import com.example.segundoapiappfixa.adapters.dto.output.OrdemServico.OrdemServicoOutputDTO;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class OrdemServicoDynamicMapper {
    public OrdemServicoOutputDTO mask(OrdemServicoOutputDTO dto, List<String> fields) {
        return new OrdemServicoOutputDTO(
                fields.contains("id") ? dto.id() : null,
                fields.contains("titulo") ? dto.titulo() : null,
                fields.contains("dataCriacao") ? dto.dataCriacao() : null,
                fields.contains("nomeUsuario") ? dto.nomeUsuario() : null,
                fields.contains("tipoAcesso") ? dto.tipoAcesso() : null);
    }
}
