package ru.rseu.gorkin.web.validators;

import java.util.List;

public class ValidationUtils {
    public static boolean validate(List<ValidationResultable> validationResults) {
        for (ValidationResultable validationResult : validationResults) {
            if (!validationResult.getValidationClass().isNormal()) {
                return false;
            }
        }
        return true;
    }
}
