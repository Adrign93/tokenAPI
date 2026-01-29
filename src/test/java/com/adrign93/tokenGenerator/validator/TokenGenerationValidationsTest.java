package com.adrign93.tokenGenerator.validator;

import com.adrign93.tokenGenerator.domain.dto.TokenRequest;
import org.junit.jupiter.api.Test;

import static com.adrign93.tokenGenerator.utils.TestUtils.generateTokenRequest;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TokenGenerationValidationsTest {

    @Test
    void testRulesNullRequest() {
        assertThatThrownBy(() -> TokenGenerationValidations.rules.get(0).validate(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Los parametros no pueden ser nulos");
    }

    @Test
    void testRulesNullUsername() {
        TokenRequest tokenRequest = generateTokenRequest();
        tokenRequest.setUsername(null);
        assertThatThrownBy(() -> TokenGenerationValidations.rules.get(1).validate(tokenRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El username no puede estar vacio");
        tokenRequest.setUsername("");
        assertThatThrownBy(() -> TokenGenerationValidations.rules.get(1).validate(tokenRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El username no puede estar vacio");
    }

    @Test
    void testRulesNullPassword() {
        TokenRequest tokenRequest = generateTokenRequest();
        tokenRequest.setPassword(null);
        assertThatThrownBy(() -> TokenGenerationValidations.rules.get(2).validate(tokenRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La contraseña no puede estar vacia");
        tokenRequest.setPassword("");
        assertThatThrownBy(() -> TokenGenerationValidations.rules.get(2).validate(tokenRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La contraseña no puede estar vacia");
    }

    @Test
    void testRulesNullEntity() {
        TokenRequest tokenRequest = generateTokenRequest();
        tokenRequest.setEntity(null);
        assertThatThrownBy(() -> TokenGenerationValidations.rules.get(3).validate(tokenRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La entity no puede estar vacia");
        tokenRequest.setEntity("");
        assertThatThrownBy(() -> TokenGenerationValidations.rules.get(3).validate(tokenRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La entity no puede estar vacia");
    }

    @Test
    void testRules() {
        assertDoesNotThrow(() -> {
            TokenGenerationValidations.rules.forEach(rule -> rule.validate(generateTokenRequest()));
        });
    }

}