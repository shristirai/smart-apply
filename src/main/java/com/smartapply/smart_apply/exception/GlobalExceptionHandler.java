package com.smartapply.smart_apply.exception;

import com.smartapply.smart_apply.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SmartApplyException.class)
    public ResponseEntity<ErrorResponse> handleSmartApplyException(
            SmartApplyException ex) {

        ErrorResponse response = ErrorResponse.builder()
                .errorCode(ex.getError().getErrorCode())
                .message(ex.getError().getErrorMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex) {

        ErrorResponse response = ErrorResponse.builder()
                .errorCode(SmartApplyErrorMessage.INTERNAL_SERVER_ERROR.getErrorCode())
                .message(SmartApplyErrorMessage.INTERNAL_SERVER_ERROR.getErrorMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(
            AccessDeniedException ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                SmartApplyErrorMessage.UNAUTHORIZED_ACCESS.getErrorCode(),
                SmartApplyErrorMessage.UNAUTHORIZED_ACCESS.getErrorMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult().getFieldError() != null
                ? ex.getBindingResult().getFieldError().getDefaultMessage()
                : SmartApplyErrorMessage.INVALID_REQUEST.getErrorMessage();

        ErrorResponse error = new ErrorResponse(
                SmartApplyErrorMessage.INVALID_REQUEST.getErrorCode(),
                message,
                LocalDateTime.now()
        );

        return ResponseEntity.badRequest().body(error);
    }
}