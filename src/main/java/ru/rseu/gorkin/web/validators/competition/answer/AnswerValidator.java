package ru.rseu.gorkin.web.validators.competition.answer;

import ru.rseu.gorkin.web.validators.NormalValidationResultable;
import ru.rseu.gorkin.web.validators.ValidationResultClasses;
import ru.rseu.gorkin.web.validators.ValidationResultable;
import ru.rseu.gorkin.web.validators.Validator;

public class AnswerValidator implements Validator<String> {

    @Override
    public ValidationResultable validate(String valueToValidate) {
        if ("".equals(valueToValidate.trim())) {
            return new ValidationResultable() {
                @Override
                public ValidationResultClasses getValidationClass() {
                    return ValidationResultClasses.ERROR;
                }

                @Override
                public String getDescription() {
                    return "Нельзя передавать пустое значение";
                }
            };
        }
        return new NormalValidationResultable();
    }
}
