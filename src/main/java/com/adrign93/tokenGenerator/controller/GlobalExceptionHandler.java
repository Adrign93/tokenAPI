package com.adrign93.tokenGenerator.controller;

import com.adrign93.tokenGenerator.domain.dto.TokenResponse;
import com.adrign93.tokenGenerator.exception.InternalServerException;
import com.adrign93.tokenGenerator.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Esta clase nos permite controlar las excepciones que se producen en el sistema
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * En caso de fallar por un fallo interno devolvemos un error 500
     * @param ex String
     * @return ResponseEntity
     */
    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<TokenResponse> handleInternalServerException(InternalServerException ex) {
        return ResponseEntity.status(500).body(TokenResponse.builder()
                        .success(false)
                        .errorCode("500")
                        .message(ex.getMessage())
                .build());
    }

    /**
     * En caso de fallar por los parametros de entrada devuelve un error 400
     * @param ex String
     * @return ResponseEntity
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<TokenResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(400).body(TokenResponse.builder()
                .success(false)
                .errorCode("400")
                .message(ex.getMessage())
                .build());
    }

    /**
     * En caso de fallar la validacion del usuario devuelve un error 404
     * @param ex String
     * @return ResponseEntity
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<TokenResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(404).body(TokenResponse.builder()
                .success(false)
                .errorCode("404")
                .message(ex.getMessage())
                .build());
    }
}
