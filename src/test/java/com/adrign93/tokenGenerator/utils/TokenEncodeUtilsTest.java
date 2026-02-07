package com.adrign93.tokenGenerator.utils;

import com.adrign93.tokenGenerator.exception.InternalServerException;
import com.adrign93.tokenGenerator.exception.TokenFormatException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Base64;


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
    void testHmacSha256() {
        byte[] result = TokenEncodeUtils.hmacSha256("test", "test");
        Assertions.assertNotNull(result);
        Assertions.assertNotEquals(0, result.length);

    }

    @Test
    void testHmacSha256TokenFormatException() {
        byte[] result = TokenEncodeUtils.hmacSha256("test", "test");
        Assertions.assertNotNull(result);
        Assertions.assertNotEquals(0, result.length);

    }

    @Test
    void testHmacSha256WhenParamsNullException()  {
        Assertions.assertThrows(TokenFormatException.class, () -> TokenEncodeUtils.hmacSha256(null, null));
    }

    @Test
    void testHmacSha256WhenDataNullException()  {
        Assertions.assertThrows(TokenFormatException.class, () -> TokenEncodeUtils.hmacSha256(null, "1234"));
    }

    @Test
    void testHmacSha256WhenSecretNullException()  {
        Assertions.assertThrows(InternalServerException.class, () -> TokenEncodeUtils.hmacSha256("data", null));
    }

    @Test
    void testGetElementOk(){
        Assertions.assertNotNull(TokenEncodeUtils.getElement("username", "eyJ1c2VybmFtZSI6IkFuYSIsImVudGl0eSI6IkRFTElWRVJZIiwiaWF0IjoxNzY5NjkwNzM0LCJleHAiOjE3Njk2OTQzMzR9"));
    }


}