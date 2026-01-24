package com.adrign93.tokenGenerator.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class TokenEncodeUtilsTest {

    @Test
    void testEncode() {
        String result = TokenEncodeUtils.encode("test".getBytes());
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("dGVzdA", result);
    }

    @Test
    void testHmacSha256() throws Exception {
        byte[] result = TokenEncodeUtils.hmacSha256("test", "test");
        Assertions.assertNotNull(result);
        Assertions.assertNotEquals(0, result.length);

    }

    @Test
    void testHmacSha256Exception()  {
        Assertions.assertThrows(Exception.class, () -> TokenEncodeUtils.hmacSha256(null, null));
    }

}