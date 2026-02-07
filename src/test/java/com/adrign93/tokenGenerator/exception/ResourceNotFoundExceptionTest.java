package com.adrign93.tokenGenerator.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ResourceNotFoundExceptionTest {

    @Test
    void testResourceNotFoundExceptionDefinition() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Test");
        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Test", exception.getMessage());

    }
}