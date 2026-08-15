package com.financial.api.framework.shared.dto.response;

public record ControllerResponseDTO<T>(
        String message,
        T data
) {
}