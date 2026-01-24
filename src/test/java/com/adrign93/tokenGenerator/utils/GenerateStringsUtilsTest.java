package com.adrign93.tokenGenerator.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.adrign93.tokenGenerator.utils.TestUtils.generateTokenRequest;

@ExtendWith(MockitoExtension.class)
class GenerateStringsUtilsTest {


    @Test
    void testGenerateHeader() {

        String result = GenerateStringsUtils.generateHeader(generateTokenRequest());
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("{\"username\":username,\"entity\":entity}", result);
    }

    @Test
    void testGeneratePayload() {
        String result = GenerateStringsUtils.generatePayload(generateTokenRequest(), 1000);
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
    }
}