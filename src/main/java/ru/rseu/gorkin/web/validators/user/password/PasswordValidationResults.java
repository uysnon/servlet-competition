package ru.rseu.gorkin.web.validators.user.password;

import ru.rseu.gorkin.web.validators.ValidationResultClasses;
import ru.rseu.gorkin.web.validators.ValidationResultable;

public enum PasswordValidationResults implements ValidationResultable {
    TOO_SHORT_PASSWORD {
        @Override
        public ValidationResultClasses getValidationClass() {
            return ValidationResultClasses.ERROR;
        }

        @Override
        public String getDescription() {
            return String.format("Длина пароля должна быть не менее %d символов", PasswordValidator.MIN_PASSWORD_LENGTH);
        }
    },

    TOO_LONG_PASSWORD {
        @Override
        public ValidationResultClasses getValidationClass() {
            return ValidationResultClasses.ERROR;
        }

        @Override
        public String getDescription() {
            return String.format("Длина пароля должна быть не более %d символов", PasswordValidator.MAX_PASSWORD_LENGTH);
        }
    },

}
