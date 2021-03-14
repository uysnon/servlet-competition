package ru.rseu.gorkin.web.commands;

import ru.rseu.gorkin.datalayer.dao.AuthenticationResults;
import ru.rseu.gorkin.datalayer.dao.DAOFactory;
import ru.rseu.gorkin.datalayer.dto.Roles;
import ru.rseu.gorkin.datalayer.dto.User;
import ru.rseu.gorkin.resources.utils.ConfigurationManagers;
import ru.rseu.gorkin.web.FrontController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginCommand implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";


    public LoginCommand() {

    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = (DAOFactory) request
                .getServletContext()
                .getAttribute(FrontController.DAO_FACTORY_CONTEXT_ATTRIBUTE);
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        AuthenticationResults authenticationResult = null;

        authenticationResult = daoFactory.getUserDAO().authenticate(login, pass);

        request.setAttribute("errorLoginPassMessage", authenticationResult.getDescription());
        if (authenticationResult == AuthenticationResults.SUCCESS) {
            User user = daoFactory.getUserDAO().get(login);
            request.getSession().setAttribute(ConfigurationManagers.WEB_MANAGER.getProperty("session.attribute.login"), login);
            request.getSession().setAttribute(ConfigurationManagers.WEB_MANAGER.getProperty("session.attribute.role"), user.getRole());
            response.sendRedirect(UrlUtils.getCommandUrl(findFirstShowCommandAccordingToUser(user)));
        } else {
            request.setAttribute("errorLoginPassMessage", authenticationResult.getDescription());
            request.getRequestDispatcher(ConfigurationManagers.WEB_MANAGER.getProperty("page.login")).forward(request, response);
        }
    }

    private String findFirstShowCommandAccordingToUser(User user) {
        Roles role = user.getRole();
        String commandTitle = null;
        if (role == Roles.ADMINISTRATOR) {
            commandTitle = CommandEnum.SHOW_USER_LIST.name();
        }
        if (role == Roles.PARTICIPANT) {
            commandTitle = CommandEnum.SHOW_ALL_COMPETITIONS.name();
        }
        if (role == Roles.EXPERT) {
            commandTitle = CommandEnum.SHOW_WORKS_TO_CHECK.name();
        }
        return commandTitle;
    }

    private String findFirstPageAccordingToUser(User user) {
        Roles role = user.getRole();
        String pageKey = null;
        if (role == Roles.ADMINISTRATOR) {
            pageKey = "page.admin.usersList";
        }
        if (role == Roles.PARTICIPANT) {
            pageKey = "page.allCompetitions";
        }
        if (role == Roles.EXPERT) {
            pageKey = "page.expert.worksToCheck";
        }
        return ConfigurationManagers.WEB_MANAGER.getProperty(pageKey);
    }
}
