package com.financial.api.shared.exception;

public class  BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
