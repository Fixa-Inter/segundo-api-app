package com.example.segundoapiappfixa.adapters.mapper;

import com.example.segundoapiappfixa.adapters.dto.output.Usuario.UsuarioOutputDTO;
import com.example.segundoapiappfixa.application.usecase.Tecnico.TecnicoResumo;
import com.example.segundoapiappfixa.domain.model.Turno;
import com.example.segundoapiappfixa.domain.model.Usuario;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.TurnoEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.TurnoUsuarioEntity;
import com.example.segundoapiappfixa.infrastructure.database.sql.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TurnoMapper.class, AptidaoMapper.class})
public interface UsuarioMapper {

    @Mapping(target = "turnos", ignore = true)
    @Mapping(target = "foto", ignore = true)
    UsuarioEntity toEntity(Usuario model);

    @Mapping(source = "turnos", target = "turnos")
    @Mapping(source = "foto.url", target = "urlFotoPerfil")
    Usuario toModel(UsuarioEntity entity);

    @Mapping(source = "tipoAcesso.nome", target = "tipoAcesso")
    @Mapping(source = "unidade.cnpj", target = "cnpjUnidade")
    @Mapping(source = "turnos", target = "turnos")
    UsuarioOutputDTO toOutputDTO(Usuario usuario);

    @Mapping(source = "tecnico.id", target = "id")
    @Mapping(source = "tecnico.nomeCompleto", target = "nomeCompleto")
    @Mapping(source = "tecnico.email", target = "email")
    @Mapping(source = "tecnico.cargo", target = "cargo")
    @Mapping(source = "tecnico.tipoAcesso.nome", target = "tipoAcesso")
    @Mapping(source = "tecnico.dataNascimento", target = "dataNascimento")
    @Mapping(source = "tecnico.unidade.cnpj", target = "cnpjUnidade")
    @Mapping(source = "tecnico.dataCriacao", target = "dataCriacao")
    @Mapping(source = "tecnico.estaAtivo", target = "estaAtivo")
    @Mapping(source = "tecnico.turnos", target = "turnos")
    @Mapping(source = "tecnico.urlFotoPerfil", target = "urlFotoPerfil")
    @Mapping(source = "competenciaVencedora", target = "competenciaVencedora")
    @Mapping(source = "tarefasEmAndamento", target = "tarefasEmAndamento")
    UsuarioOutputDTO toOutputDTO(TecnicoResumo tecnicoResumo);

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
