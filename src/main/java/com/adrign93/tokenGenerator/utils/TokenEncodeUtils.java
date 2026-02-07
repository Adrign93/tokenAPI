package com.adrign93.tokenGenerator.utils;

import com.adrign93.tokenGenerator.exception.InternalServerException;
import com.adrign93.tokenGenerator.exception.TokenFormatException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
     */
    public static byte[] hmacSha256(String data, String secret)  {
        if(data == null ) {
            throw new TokenFormatException("El token no tiene el formato correcto");
        }
        if(secret == null ){
            throw new InternalServerException("Propiedades mal configuradas");
        }
        byte[] hash = secret.getBytes(StandardCharsets.UTF_8);
        Mac sha256Hmac;
        try {
            sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
            sha256Hmac.init(secretKey);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new TokenFormatException("Se ha producido un error al generar la firma del token debido a su formato");
        }
        return sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Obtiene un elemento del payload
     * @param element String
     * @param encodedPayload String
     * @return JsonNode
     */
    public static JsonNode getElement(String element, String encodedPayload){
        // Decodificar Payload para obtener datos
        String payloadJson = new String(Base64.getUrlDecoder().decode(encodedPayload), StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode payloadNode;
        try {
            payloadNode = objectMapper.readTree(payloadJson);
            return payloadNode.get(element);
        } catch (JsonProcessingException e) {
            throw new TokenFormatException("El token no tiene el formato correcto");
        }
    }
}
