package ru.rseu.gorkin.web.validators.user.password;

import ru.rseu.gorkin.web.validators.NormalValidationResultable;
import ru.rseu.gorkin.web.validators.ValidationResultable;
import ru.rseu.gorkin.web.validators.Validator;

public class PasswordValidator implements Validator<String> {
    public static final int MAX_PASSWORD_LENGTH = 20;
    public static final int MIN_PASSWORD_LENGTH = 4;

    @Override
    public ValidationResultable validate(String valueToValidate) {
        if (valueToValidate.length() < MIN_PASSWORD_LENGTH){
            return PasswordValidationResults.TOO_SHORT_PASSWORD;
        }
        if (valueToValidate.length() > MAX_PASSWORD_LENGTH){
            return PasswordValidationResults.TOO_LONG_PASSWORD;
        }
        return new NormalValidationResultable();
    }
}
