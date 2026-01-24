package com.adrign93.tokenGenerator.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class TokenEncodeUtils {

    /**
     * Codifica los bytes en Base64
     * @param bytes byte[]
     * @return String
     */
    public static String encode(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    /**
     * Metodo auxiliar para generar la firma del token usando el sha256
     * @param data String
     * @param secret String
     * @return byte[]
     * @throws Exception
     */
    public static byte[] hmacSha256(String data, String secret) throws Exception {
        byte[] hash = secret.getBytes(StandardCharsets.UTF_8);
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
        sha256Hmac.init(secretKey);
        return sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }
}
