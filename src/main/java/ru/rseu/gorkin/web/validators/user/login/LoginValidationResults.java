package ru.rseu.gorkin.web.validators.user.login;

import ru.rseu.gorkin.web.validators.ValidationResultClasses;
import ru.rseu.gorkin.web.validators.ValidationResultable;

public enum LoginValidationResults implements ValidationResultable {
    TOO_SHORT_LOGIN {
        @Override
        public ValidationResultClasses getValidationClass() {
            return ValidationResultClasses.ERROR;
        }

        @Override
        public String getDescription() {
            return String.format("Логин должен быть не менее %d символов!", LoginValidator.LOGIN_MIN_LENGTH);
        }
    },
    REPEATABLE_LOGIN {
        @Override
        public ValidationResultClasses getValidationClass() {
            return ValidationResultClasses.ERROR;
        }

        @Override
        public String getDescription() {
            return "Уже существует пользователь с таким логином!";
        }
    },
    TOO_LONG_LOGIN {
        @Override
        public ValidationResultClasses getValidationClass() {
            return ValidationResultClasses.ERROR;
        }

        @Override
        public String getDescription() {
            return String.format("Логин должен быть не более %d символов!", LoginValidator.LOGIN_MAX_LENGTH);
        }
    },

}
