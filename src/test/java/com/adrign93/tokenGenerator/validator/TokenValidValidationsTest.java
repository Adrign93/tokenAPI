package com.adrign93.tokenGenerator.validator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TokenValidValidationsTest {

    @Test
    void testRulesNullRequest() {
        assertThatThrownBy(() -> TokenValidValidations.rules.get(0).validate(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El token no puede ser nulo");
    }
}