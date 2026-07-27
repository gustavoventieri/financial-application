package com.financial.api.user.domain;

import com.financial.api.shared.enumerated.Roles;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder(toBuilder = true)
public record User(
        String id,
        String name,
        String email,
        String password,
        Roles role,
        boolean isVerified,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static User create(
            String name,
            String email,
            String hashedPassword
    ) {

        LocalDateTime now = LocalDateTime.now();

        return User.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .email(email)
                .password(hashedPassword)
                .role(Roles.USER)
                .isVerified(false)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public User verify() {
        return this.toBuilder()
                .isVerified(true)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public User updateRegistration(
            String name,
            String hashedPassword
    ) {
        return this.toBuilder()
                .name(name)
                .password(hashedPassword)
                .updatedAt(LocalDateTime.now())
                .build();
    }
}