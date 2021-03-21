package ru.rseu.gorkin.web.validators.competition.experts;

import ru.rseu.gorkin.web.validators.ValidationResultClasses;
import ru.rseu.gorkin.web.validators.ValidationResultable;

public enum ExpertsValidationResults implements ValidationResultable {
    NO_EXPERTS{
        @Override
        public ValidationResultClasses getValidationClass() {
            return ValidationResultClasses.ERROR;
        }

        @Override
        public String getDescription() {
            return "Должен быть выбран как минимум 1 эксперт";
        }
    };
}
