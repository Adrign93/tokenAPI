package com.adrign93.tokenGenerator.exception;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InternalServerExceptionTest {

    @Test
    void constructorTest() {
        InternalServerException exception = new InternalServerException("Test");
        Assertions.assertNotNull(exception);
        Assertions.assertEquals( "Test", exception.getMessage());
    }

}