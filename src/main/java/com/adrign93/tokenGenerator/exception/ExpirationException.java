package com.adrign93.tokenGenerator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepcion para controlar cuando un token ha expirado
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ExpirationException extends RuntimeException{

    /**
     * Constructor
     * @param message String
     */
    public ExpirationException(String message) {
        super(message);
    }
}
