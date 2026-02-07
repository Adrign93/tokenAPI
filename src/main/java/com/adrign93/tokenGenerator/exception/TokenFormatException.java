package com.adrign93.tokenGenerator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción para el formato del token
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TokenFormatException extends RuntimeException {

    /**
     * Constructror del token
     * @param message String
     */
    public TokenFormatException(String message) {
        super(message);
    }
}
