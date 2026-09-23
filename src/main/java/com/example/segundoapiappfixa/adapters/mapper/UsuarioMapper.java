package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.adapters.dto.output.Usuario.TecnicoOutputDTO;
import com.example.segundoapiappfixa.adapters.dto.output.Usuario.UsuarioOutputDTO;
import com.example.segundoapiappfixa.domain.model.Turno;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.infrastructure.database.entity.TurnoEntity;
import com.example.segundoapiappfixa.infrastructure.database.entity.TurnoUsuarioEntity;
import com.example.segundoapiappfixa.infrastructure.database.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = TurnoMapper.class)
public interface UsuarioMapper {

    @Mapping(target = "turnos", ignore = true)
    UsuarioEntity toEntity(Usuario model);

    @Mapping(source = "turnos", target = "turnos")
    Usuario toModel(UsuarioEntity entity);

    @Mapping(source = "tipoAcesso.nome", target = "tipoAcesso")
    @Mapping(source = "endereco.cnpj", target = "cnpjEndereco")
    @Mapping(source = "turnos", target = "turnos")
    UsuarioOutputDTO toOutputDTO(Usuario usuario);

    @Mapping(source = "tipoAcesso.nome", target = "tipoAcesso")
    @Mapping(source = "turnos", target = "turnos")
    TecnicoOutputDTO toTecnicoOutputDTO(Usuario usuario);

    default List<Turno> mapTurnos(List<TurnoUsuarioEntity> turnos) {
        if (turnos == null) return List.of();

        return turnos.stream()
                .filter(item -> item.getTurno() != null)
                .map(TurnoUsuarioEntity::getTurno)
                .map(this::toTurno)
                .toList();
    }
    Turno toTurno(TurnoEntity entity);


}
