package com.example.segundoapiappfixa.infrastructure.database.sql.converter;

import com.example.segundoapiappfixa.domain.enums.TipoLocalEndereco;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TipoLocalEnderecoConverter implements AttributeConverter<TipoLocalEndereco, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TipoLocalEndereco attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public TipoLocalEndereco convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : TipoLocalEndereco.fromId(dbData);
    }
}
