package com.example.segundoapiappfixa.domain.enums.converter;

import com.example.segundoapiappfixa.domain.enums.CategoriaProblema;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoriaProblemaConverter implements AttributeConverter<CategoriaProblema, Integer> {
    @Override
    public Integer convertToDatabaseColumn(CategoriaProblema attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public CategoriaProblema convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : CategoriaProblema.fromId(dbData);
    }
}
