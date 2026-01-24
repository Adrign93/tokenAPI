package com.adrign93.tokenGenerator.sanitizer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import java.io.IOException;

/**
 * Deserializador para limpiar etiquetas HTML
 */
public class HtmlSanitizerSerializer extends JsonDeserializer<String> {
    /**
     * Deserializa el valor del JSON
     * @param parser JsonParser
     * @param ctxt DeserializationContext
     * @return String
     * @throws IOException Exception
     */
    @Override
    public String deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        String value = parser.getValueAsString();
        if (value == null) return null;
        // Limpia etiquetas HTML peligrosas
        return Jsoup.clean(value, Safelist.none());
    }
}
