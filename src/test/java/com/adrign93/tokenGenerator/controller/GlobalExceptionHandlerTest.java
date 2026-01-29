package com.adrign93.tokenGenerator.controller;


import com.adrign93.tokenGenerator.domain.dto.TokenResponse;
import com.adrign93.tokenGenerator.exception.InternalServerException;
import com.adrign93.tokenGenerator.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void testHandleInternalServerException() {
        InternalServerException exception = new InternalServerException("Test");
        ResponseEntity<TokenResponse> response = globalExceptionHandler.handleInternalServerException(exception);
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.getStatusCode().is5xxServerError());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("Test", response.getBody().getError().getMessage());
        Assertions.assertFalse(response.getBody().isSuccess());
    }


    @Test
    void testHandleIllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("Test");
        ResponseEntity<TokenResponse> response = globalExceptionHandler.handleIllegalArgumentException(exception);
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("Test", response.getBody().getError().getMessage());
        Assertions.assertFalse(response.getBody().isSuccess());
    }

    @Test
    void testHandleResourceNotFoundException() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Test");
        ResponseEntity<TokenResponse> response = globalExceptionHandler.handleResourceNotFoundException(exception);
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("Test", response.getBody().getError().getMessage());
        Assertions.assertFalse(response.getBody().isSuccess());
    }
}