package ru.rseu.gorkin.web.validators;

import javax.servlet.ServletContext;

public interface Validator<T> {
    ValidationResultable validate(T valueToValidate);

    default ValidationResultable validate(T valueToValidate, ServletContext context) {
        return validate(valueToValidate);
    }
}
