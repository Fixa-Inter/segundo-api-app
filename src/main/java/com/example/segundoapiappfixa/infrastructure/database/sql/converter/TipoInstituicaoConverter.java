package com.example.segundoapiappfixa.infrastructure.database.sql.converter;

import com.example.segundoapiappfixa.domain.enums.TipoInstituicao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TipoInstituicaoConverter implements AttributeConverter<TipoInstituicao, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TipoInstituicao attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public TipoInstituicao convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : TipoInstituicao.fromId(dbData);
    }
}
