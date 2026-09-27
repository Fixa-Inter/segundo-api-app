package com.example.segundoapiappfixa.infrastructure.database.repository.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;

@UtilityClass
public class SortUtils {

    public static Sort definirSort(String campoOrdenacao, Integer direcaoOrdenacao) {
        Sort sort = Sort.unsorted();

        if (campoOrdenacao != null && direcaoOrdenacao != null) {
            Sort.Direction direction =
                    direcaoOrdenacao == 1
                            ? Sort.Direction.ASC
                            : Sort.Direction.DESC;

            sort = Sort.by(
                    direction,
                    campoOrdenacao
            );
        }

        return sort;
    }

    public static String normalizarCampoOrdenacao(String campoOrdenacao, Map<String, String> camposNormalizados) {
        if (campoOrdenacao == null || campoOrdenacao.isBlank()) {
            return null;
        }

        for (String key : camposNormalizados.keySet()) {
            if (key.equalsIgnoreCase(campoOrdenacao)
                    || camposNormalizados.get(key).equalsIgnoreCase(campoOrdenacao)) {
                return camposNormalizados.get(key);
            }
        }

        return null;
    }

}
