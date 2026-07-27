package com.financial.api.user.domain;

import com.financial.api.shared.enumerated.Roles;
import lombok.Builder;

import java.time.LocalDateTime;

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


){
}
