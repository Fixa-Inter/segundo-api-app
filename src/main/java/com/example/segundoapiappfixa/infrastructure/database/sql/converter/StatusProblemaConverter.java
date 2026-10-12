package com.example.segundoapiappfixa.infrastructure.database.sql.converter;

import com.example.segundoapiappfixa.domain.enums.StatusProblema;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StatusProblemaConverter implements AttributeConverter<StatusProblema, Integer> {
    @Override
    public Integer convertToDatabaseColumn(StatusProblema attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public StatusProblema convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : StatusProblema.fromId(dbData);
    }
}
