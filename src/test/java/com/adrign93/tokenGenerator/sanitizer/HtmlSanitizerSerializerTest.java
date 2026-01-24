package com.adrign93.tokenGenerator.sanitizer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HtmlSanitizerSerializerTest {

    @InjectMocks
    private HtmlSanitizerSerializer deserializer;
    private JsonParser jsonParser;
    private DeserializationContext context;

    @BeforeEach
    void setUp() {
        jsonParser = Mockito.mock(JsonParser.class);
        context = Mockito.mock(DeserializationContext.class);
    }

    @Test
    void testDeserializeNull() throws IOException {
        Mockito.when(jsonParser.getValueAsString()).thenReturn(null);

        String result = deserializer.deserialize(jsonParser, context);

        assertNull(result);
    }

    @Test
    void testDeserializeWithXss() throws IOException {
        String dirtyHtml = "<script>alert('xss')</script>Hola Mundo";
        Mockito.when(jsonParser.getValueAsString()).thenReturn(dirtyHtml);

        String result = deserializer.deserialize(jsonParser, context);

        assertEquals("Hola Mundo", result.trim());
    }

    @Test
    void testDeserializeWithHtmlTags() throws IOException {
        String dirtyHtml = "<div>Texto en <b>negrita</b></div>";
        Mockito.when(jsonParser.getValueAsString()).thenReturn(dirtyHtml);

        String result = deserializer.deserialize(jsonParser, context);

        // Safelist.none() elimina TODO, incluyendo negritas
        assertEquals("Texto en negrita", result.trim());
    }

    @Test
    void testDeserializePlainString() throws IOException {
        String plainText = "Texto normal sin HTML";
        Mockito.when(jsonParser.getValueAsString()).thenReturn(plainText);

        String result = deserializer.deserialize(jsonParser, context);

        assertEquals("Texto normal sin HTML", result);
    }
}