package com.example.segundoapiappfixa.infrastructure.database.sql.converter;

import com.example.segundoapiappfixa.domain.enums.Prioridade;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PrioridadeConverter implements AttributeConverter<Prioridade, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Prioridade attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public Prioridade convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : Prioridade.fromId(dbData);
    }
}
