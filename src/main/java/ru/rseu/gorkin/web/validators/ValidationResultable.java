package ru.rseu.gorkin.web.validators;

public interface ValidationResultable {
    ValidationResultClasses getValidationClass();
    String getDescription();
}
