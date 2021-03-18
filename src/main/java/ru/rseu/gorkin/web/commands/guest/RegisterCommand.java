package ru.rseu.gorkin.web.commands.guest;


import ru.rseu.gorkin.datalayer.dao.DAOFactory;
import ru.rseu.gorkin.datalayer.dto.Roles;
import ru.rseu.gorkin.web.FrontController;
import ru.rseu.gorkin.web.commands.Command;
import ru.rseu.gorkin.web.validators.ValidationResultable;
import ru.rseu.gorkin.web.validators.Validator;
import ru.rseu.gorkin.web.validators.user.login.LoginValidator;
import ru.rseu.gorkin.web.validators.user.password.Pair;
import ru.rseu.gorkin.web.validators.user.password.PasswordRepeatableValidator;
import ru.rseu.gorkin.web.validators.user.password.PasswordValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RegisterCommand implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_NAME = "name";
    private static final String PARAM_NAME_PASSWORD_REPEAT = "password_repeat";

    private static final String ATTRIBUTE_NAME_SUCCESS_REGISTRATION = "registering_message";
    private static final String ATTRIBUTE_NAME_LOGIN_VALIDATE_RESULT = "login_validation";
    private static final String ATTRIBUTE_NAME_PASSWORD_VALIDATE_RESULT = "password_validation";
    private static final String ATTRIBUTE_NAME_PASSWORD_REPEAT_VALIDATE_RESULT = "password_repeat_validation";

    private Validator<String> loginValidator;
    private Validator<String> passwordValidator;
    private Validator<Pair<String, String>> repeatPasswordValidator;

    public RegisterCommand() {
        loginValidator = new LoginValidator();
        passwordValidator = new PasswordValidator();
        repeatPasswordValidator = new PasswordRepeatableValidator();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = (DAOFactory) request
                .getServletContext()
                .getAttribute(FrontController.DAO_FACTORY_CONTEXT_ATTRIBUTE);

        String login = request.getParameter(PARAM_NAME_LOGIN);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        String name = request.getParameter(PARAM_NAME_NAME);
        String passwordRepeat = request.getParameter(PARAM_NAME_PASSWORD_REPEAT);

        ValidationResultable loginValidationResult = loginValidator.validate(login, request.getServletContext());
        ValidationResultable passwordValidationResult = passwordValidator.validate(password);
        ValidationResultable repeatPasswordValidationResult = repeatPasswordValidator.validate(new Pair<>(password, passwordRepeat));

        if (validate(Stream.of(loginValidationResult, passwordValidationResult, repeatPasswordValidationResult).collect(Collectors.toList()))) {
            daoFactory.getUserDAO().createUser(login, password, name, Roles.PARTICIPANT);
            request.setAttribute(ATTRIBUTE_NAME_SUCCESS_REGISTRATION, "Регистрация прошла успешно!");
            new ShowLoginPageCommand().execute(request, response);
        } else {
            request.setAttribute(ATTRIBUTE_NAME_LOGIN_VALIDATE_RESULT, loginValidationResult);
            request.setAttribute(ATTRIBUTE_NAME_PASSWORD_VALIDATE_RESULT, passwordValidationResult);
            request.setAttribute(ATTRIBUTE_NAME_PASSWORD_REPEAT_VALIDATE_RESULT, repeatPasswordValidationResult);


            request.setAttribute(PARAM_NAME_PASSWORD_REPEAT, passwordRepeat);
            request.setAttribute(PARAM_NAME_PASSWORD, password);
            request.setAttribute(PARAM_NAME_LOGIN, login);
            request.setAttribute(PARAM_NAME_NAME, name);
            new ShowRegistrationPageCommand().execute(request, response);
        }

    }

    private boolean validate(List<ValidationResultable> validationResults) {
        for (ValidationResultable validationResult : validationResults) {
            if (!validationResult.getValidationClass().isNormal()) {
                return false;
            }
        }
        return true;
    }
}
