package com.adrign93.tokenGenerator.exception;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TokenFormatExceptionTest {

    @Test
    void testTokenFormatExceptionDefinition() {
        TokenFormatException exception = new TokenFormatException("Test");
        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Test", exception.getMessage());
    }
}