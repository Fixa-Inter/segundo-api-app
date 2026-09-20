package com.example.segundoapiappfixa.adapters.mapper.dynamic;

import com.example.segundoapiappfixa.infrastructure.exception.RegraProblemaException;

import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.List;

public final class DynamicFieldFilter {

    private DynamicFieldFilter() {
    }

    public static List<String> availableFields(Record dto) {
        return Arrays
                .stream(dto.getClass().getRecordComponents())
                .map(RecordComponent::getName)
                .toList();
    }

    public static List<String> selectedFields(String fields, List<String> availableFields) {
        if (fields == null || fields.isBlank()) {
            return availableFields;
        }

        List<String> selectedFields = Arrays.stream(fields.split(","))
                .map(String::trim)
                .filter(field -> !field.isBlank())
                .distinct()
                .toList();

        if (selectedFields.stream().anyMatch(field -> !availableFields.contains(field))) {
            throw new RegraProblemaException("exception.field.invalid");
        }

        return selectedFields;
    }
}
