package ru.rseu.gorkin.web.commands.loggedin;

import ru.rseu.gorkin.datalayer.dao.DAOFactory;
import ru.rseu.gorkin.resources.utils.ConfigurationManagers;
import ru.rseu.gorkin.web.servlets.FrontController;
import ru.rseu.gorkin.web.commands.Command;
import ru.rseu.gorkin.web.commands.CommandEnum;
import ru.rseu.gorkin.web.commands.UrlUtils;
import ru.rseu.gorkin.web.validators.ValidationResultable;
import ru.rseu.gorkin.web.validators.ValidationUtils;
import ru.rseu.gorkin.web.validators.Validator;
import ru.rseu.gorkin.web.validators.user.password.Pair;
import ru.rseu.gorkin.web.validators.user.password.PasswordRepeatableValidator;
import ru.rseu.gorkin.web.validators.user.password.PasswordValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EditAccountByUserCommand implements Command {
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_NAME = "name";
    private static final String PARAM_NAME_PASSWORD_REPEAT = "password_repeat";

    private static final String ATTRIBUTE_NAME_SUCCESS_EDIT = "edit_message";
    private static final String ATTRIBUTE_NAME_PASSWORD_VALIDATE_RESULT = "password_validation";
    private static final String ATTRIBUTE_NAME_PASSWORD_REPEAT_VALIDATE_RESULT = "password_repeat_validation";

    private Validator<String> passwordValidator;
    private Validator<Pair<String, String>> repeatPasswordValidator;

    public EditAccountByUserCommand() {
        passwordValidator = new PasswordValidator();
        repeatPasswordValidator = new PasswordRepeatableValidator();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = (DAOFactory) request
                .getServletContext()
                .getAttribute(FrontController.DAO_FACTORY_CONTEXT_ATTRIBUTE);

        int userId = (Integer) request.getSession().getAttribute(ConfigurationManagers.WEB_MANAGER.getProperty("session.attribute.id"));

        String password = request.getParameter(PARAM_NAME_PASSWORD);
        String name = request.getParameter(PARAM_NAME_NAME);
        String passwordRepeat = request.getParameter(PARAM_NAME_PASSWORD_REPEAT);

        if (name == null || "".equals(name)) {
            name = daoFactory.getUserDAO().get(userId).getName();
        }

        ValidationResultable passwordValidationResult = passwordValidator.validate(password);
        ValidationResultable repeatPasswordValidationResult = repeatPasswordValidator.validate(new Pair<>(password, passwordRepeat));

        if (ValidationUtils.validate(Stream.of(passwordValidationResult, repeatPasswordValidationResult).collect(Collectors.toList()))) {
            daoFactory.getUserDAO().editUser(userId, name, password);
            request.setAttribute(ATTRIBUTE_NAME_SUCCESS_EDIT, "Успешно!");
            response.sendRedirect(UrlUtils.getCommandUrl(CommandEnum.SHOW_EDIT_ACCOUNT_BY_USER.name().toLowerCase()));
        } else {
            request.setAttribute(ATTRIBUTE_NAME_PASSWORD_VALIDATE_RESULT, passwordValidationResult);
            request.setAttribute(ATTRIBUTE_NAME_PASSWORD_REPEAT_VALIDATE_RESULT, repeatPasswordValidationResult);

            request.setAttribute(PARAM_NAME_PASSWORD_REPEAT, passwordRepeat);
            request.setAttribute(PARAM_NAME_PASSWORD, password);
            request.setAttribute(PARAM_NAME_NAME, name);
            new ShowEditAccountByUserPageCommand().execute(request, response);
        }

    }
}
