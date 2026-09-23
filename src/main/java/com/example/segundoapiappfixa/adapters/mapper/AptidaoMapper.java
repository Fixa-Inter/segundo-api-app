package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.adapters.dto.output.Aptidao.AptidaoOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.CategoriaEquipamento.CategoriaEquipamentoOutputDTO;
import com.example.segundoapiappfixa.domain.model.Aptidao;
import com.example.segundoapiappfixa.domain.model.CategoriaEquipamento;
import com.example.segundoapiappfixa.infrastructure.database.entity.AptidaoEntity;
import com.example.segundoapiappfixa.infrastructure.database.entity.CategoriaEquipamentoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AptidaoMapper {

    AptidaoEntity toEntity(Aptidao model);
    Aptidao toModel(AptidaoEntity entity);

    @Mapping(source = "categoriaProblema.nome", target = "categoriaProblema")
    AptidaoOutputDTO toOutputDTO(Aptidao aptidao);

}
