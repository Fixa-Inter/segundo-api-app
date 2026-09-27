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
                fields.contains("descricao") ? dto.descricao() : null,
                fields.contains("categoriaProblema") ? dto.categoriaProblema() : null,
                fields.contains("categoriaEquipamento") ? dto.categoriaEquipamento() : null,
                fields.contains("prioridade") ? dto.prioridade() : null,
                fields.contains("dataPrevista") ? dto.dataPrevista() : null,
                fields.contains("status") ? dto.status() : null,
                fields.contains("nomeUsuario") ? dto.nomeUsuario() : null,
                fields.contains("tipoAcesso") ? dto.tipoAcesso() : null,
                fields.contains("dataCriacao") ? dto.dataCriacao() : null);
    }
}
