package ru.rseu.gorkin.web.validators;

public class NormalValidationResultable implements ValidationResultable {
    @Override
    public ValidationResultClasses getValidationClass() {
        return ValidationResultClasses.OK;
    }

    @Override
    public String getDescription() {
        return "";
    }
}
