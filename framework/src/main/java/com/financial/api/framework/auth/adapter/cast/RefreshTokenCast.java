package com.financial.api.framework.auth.adapter.cast;

import com.financial.api.auth.domain.RefreshToken;
import com.financial.api.framework.auth.adapter.out.persistence.entity.RefreshTokenEntity;
import com.financial.api.framework.user.adapter.out.persistence.UserEntity;

public class RefreshTokenCast {

    public static RefreshTokenEntity toEntity(RefreshToken token) {

        return RefreshTokenEntity.builder()
                .id(token.id())
                .user(
                        UserEntity.builder()
                                .id(token.userId())
                                .build()
                )
                .tokenHash(token.tokenHash())
                .ip(token.ip())
                .device(token.device())
                .createdAt(token.createdAt())
                .expiresAt(token.expiresAt())
                .revokedAt(token.revokedAt())
                .build();
    }

    public static RefreshToken toDomain(RefreshTokenEntity entity) {

        return new RefreshToken(
                entity.getId(),
                entity.getUser().getId(),
                entity.getTokenHash(),
                entity.getIp(),
                entity.getDevice(),
                entity.getCreatedAt(),
                entity.getExpiresAt(),
                entity.getRevokedAt()
        );
    }


}