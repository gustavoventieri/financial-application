package com.financial.api.user.domain;

import com.financial.api.shared.enumerated.Roles;

import java.time.LocalDateTime;


public record User(
        String id,
        String name,
        String email,
        String password,
        Roles role,
        boolean isVerified,
        LocalDateTime createdAt,
        LocalDateTime updatedAt


){
}
