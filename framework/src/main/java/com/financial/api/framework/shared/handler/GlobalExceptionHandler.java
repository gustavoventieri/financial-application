package com.financial.api.framework.shared.handler;

import com.financial.api.framework.shared.handler.dto.ErrorResponse;
import com.financial.api.shared.exception.BusinessException;
import com.financial.api.shared.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex, HttpServletRequest request) {

        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(BusinessException ex, HttpServletRequest request) {

        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(HttpStatusCode status, String message, String path) {
        HttpStatus httpStatus = HttpStatus.resolve(status.value());
        String errorDescription = (httpStatus != null) ? httpStatus.getReasonPhrase() : "Unknown Error";

        return ResponseEntity.status(status).body(
                ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(status.value())
                        .error(errorDescription)
                        .message(message)
                        .path(path)
                        .build());
    }
}
