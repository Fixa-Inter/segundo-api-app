package com.example.segundoapiappfixa.infrastructure.database.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@RedisHash(value = "refresh_token", timeToLive = RefreshTokenEntity.TTL_SECONDS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenEntity {
    public static final long TTL_SECONDS = 7_776_000L;

    @Id
    private String tokenHash;

    @Indexed
    private Long usuarioId;

    @Indexed
    private String familiaId;

    private Boolean ativo;
    private LocalDateTime criadoEm;
}
