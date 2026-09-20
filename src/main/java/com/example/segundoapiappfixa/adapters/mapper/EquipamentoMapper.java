package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.domain.model.Equipamento;
import com.example.segundoapiappfixa.domain.model.Foto;
import com.example.segundoapiappfixa.infrastructure.database.entity.EquipamentoEntity;
import com.example.segundoapiappfixa.infrastructure.database.entity.FotoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EquipamentoMapper {

    EquipamentoEntity toEntity(Equipamento model);
    Equipamento toModel(EquipamentoEntity entity);

}
