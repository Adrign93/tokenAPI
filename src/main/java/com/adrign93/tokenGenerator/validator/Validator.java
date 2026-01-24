package com.adrign93.tokenGenerator.validator;

import java.util.List;

@FunctionalInterface
public interface Validator <T, E extends Exception> {

    void validate(T target) throws E;

    static <T, E extends Exception> void applyRules(List<Validator<T, E>> rules, T target) throws E {
        for(Validator<T,E> rule : rules) {
            rule.validate(target);
        }
    }
}
