package com.adrign93.tokenGenerator.exception;

/**
 * Excepcion para lanzar un 500 si falla el algoritmo de conversion
 */
public class InternalServerException extends RuntimeException{

    /**
     * Constructor
     * @param message String
     */
    public InternalServerException(String message) {
        super(message);
    }
}
