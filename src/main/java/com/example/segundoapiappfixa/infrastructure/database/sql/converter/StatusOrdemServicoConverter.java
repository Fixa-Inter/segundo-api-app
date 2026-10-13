package com.example.segundoapiappfixa.infrastructure.database.sql.converter;

import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import com.example.segundoapiappfixa.domain.enums.StatusOrdemServico;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StatusOrdemServicoConverter implements AttributeConverter<StatusOrdemServico, Integer> {
    public Integer convertToDatabaseColumn(StatusOrdemServico attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public StatusOrdemServico convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : StatusOrdemServico.fromId(dbData);
    }
}
