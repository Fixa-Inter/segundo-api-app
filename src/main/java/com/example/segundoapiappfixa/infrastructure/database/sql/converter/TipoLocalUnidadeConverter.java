package com.example.segundoapiappfixa.infrastructure.database.sql.converter;

import com.example.segundoapiappfixa.domain.enums.TipoLocalUnidade;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TipoLocalUnidadeConverter implements AttributeConverter<TipoLocalUnidade, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TipoLocalUnidade attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public TipoLocalUnidade convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : TipoLocalUnidade.fromId(dbData);
    }
}
