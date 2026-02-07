package com.adrign93.tokenGenerator.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ExpirationExceptionTest {

    @Test
    void testExpirationExceptionDeclaration() {
        ExpirationException exception = new ExpirationException("Test");
        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Test", exception.getMessage());
    }
}