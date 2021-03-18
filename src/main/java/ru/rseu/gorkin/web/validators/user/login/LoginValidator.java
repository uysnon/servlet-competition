package ru.rseu.gorkin.web.validators.user.login;

import ru.rseu.gorkin.datalayer.dao.DAOFactory;
import ru.rseu.gorkin.web.FrontController;
import ru.rseu.gorkin.web.validators.NormalValidationResultable;
import ru.rseu.gorkin.web.validators.ValidationResultable;
import ru.rseu.gorkin.web.validators.Validator;

import javax.servlet.ServletContext;

public class LoginValidator implements Validator<String> {
    public static final int LOGIN_MIN_LENGTH = 3;
    public static final int LOGIN_MAX_LENGTH = 50;

    @Override
    public ValidationResultable validate(String valueToValidate) {
        if (valueToValidate.length() < LOGIN_MIN_LENGTH) {
            return LoginValidationResults.TOO_SHORT_LOGIN;
        }
        if (valueToValidate.length() > LOGIN_MAX_LENGTH) {
            return LoginValidationResults.TOO_LONG_LOGIN;
        }
        return new NormalValidationResultable();
    }

    @Override
    public ValidationResultable validate(String valueToValidate, ServletContext context) {
        ValidationResultable baseValidateResult = validate(valueToValidate);
        if (!baseValidateResult.getValidationClass().isNormal()) {
            return baseValidateResult;
        }
        DAOFactory daoFactory = (DAOFactory) context.getAttribute(FrontController.DAO_FACTORY_CONTEXT_ATTRIBUTE);
        if (daoFactory.getUserDAO().isLoginExist(valueToValidate)){
            return LoginValidationResults.REPEATABLE_LOGIN;
        }
        return new NormalValidationResultable();
    }
}
