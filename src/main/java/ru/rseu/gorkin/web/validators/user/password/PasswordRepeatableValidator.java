package ru.rseu.gorkin.web.validators.user.password;

import ru.rseu.gorkin.web.validators.NormalValidationResultable;
import ru.rseu.gorkin.web.validators.ValidationResultClasses;
import ru.rseu.gorkin.web.validators.ValidationResultable;
import ru.rseu.gorkin.web.validators.Validator;

public class PasswordRepeatableValidator implements Validator<Pair<String, String>> {
    @Override
    public ValidationResultable validate(Pair<String, String> valueToValidate) {
        if (!valueToValidate.fst.equals(valueToValidate.snd)){
            return new ValidationResultable() {
                @Override
                public ValidationResultClasses getValidationClass() {
                    return ValidationResultClasses.ERROR;
                }

                @Override
                public String getDescription() {
                    return "Пароли не совпадают";
                }
            };
        };
        return new NormalValidationResultable();
    }
}
