package com.example.segundoapiappfixa.domain.enums.converter;

import com.example.segundoapiappfixa.domain.enums.TipoAcesso;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoAcessoConverter implements AttributeConverter<TipoAcesso, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TipoAcesso attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public TipoAcesso convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : TipoAcesso.fromId(dbData);
    }
}
