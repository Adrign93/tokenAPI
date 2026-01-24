package com.adrign93.tokenGenerator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepcion para controlar cuando no se encuentra el recurso solicitado
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    /**
     * Constructor
     * @param message String
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
