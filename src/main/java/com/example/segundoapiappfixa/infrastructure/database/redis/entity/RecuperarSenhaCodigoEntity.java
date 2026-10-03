package com.example.segundoapiappfixa.infrastructure.database.redis.entity;

import com.example.segundoapiappfixa.infrastructure.database.sql.entity.UsuarioEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Date;

@RedisHash(value = "recuperar_senha_codigo", timeToLive = 180L)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecuperarSenhaCodigoEntity {
    @Id
    private Long id;
    private Integer codigo;

    @Indexed
    public Long usuarioId;

    @Indexed
    private Boolean estaAtivo;
}
