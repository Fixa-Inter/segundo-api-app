package com.example.segundoapiappfixa.infrastructure.database.sql.converter;

import com.example.segundoapiappfixa.domain.enums.Dificuldade;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class DificuldadeConverter implements AttributeConverter<Dificuldade, Integer> {
    public Integer convertToDatabaseColumn(Dificuldade value) {
        return value == null ? null : value.getId();
    }

    public Dificuldade convertToEntityAttribute(Integer value) {
        return value == null ? null : Dificuldade.fromId(value);
    }
}
