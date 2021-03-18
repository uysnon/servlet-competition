package ru.rseu.gorkin.web.commands.admin;

import ru.rseu.gorkin.datalayer.dao.DAOFactory;
import ru.rseu.gorkin.datalayer.dto.Roles;
import ru.rseu.gorkin.datalayer.dto.User;
import ru.rseu.gorkin.resources.utils.ConfigurationManagers;
import ru.rseu.gorkin.web.FrontController;
import ru.rseu.gorkin.web.commands.Command;
import ru.rseu.gorkin.web.commands.CommandEnum;
import ru.rseu.gorkin.web.commands.UrlUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnblockCommand implements Command {
    private static final String NO_SUCH_ROLE = "Недостаточно прав пользователя для разблокировки";
    private static final String CANT_UNBLOCK = "Выбранный пользователь не существует или является администратором";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = (DAOFactory) request
                .getServletContext()
                .getAttribute(FrontController.DAO_FACTORY_CONTEXT_ATTRIBUTE);

        User currentUser = daoFactory.getUserDAO().get((String) request.getSession().getAttribute(ConfigurationManagers.WEB_MANAGER.getProperty("session.attribute.login")));
        if (currentUser == null || currentUser.getRole() != Roles.ADMINISTRATOR) {
            request.setAttribute("errorMessage", NO_SUCH_ROLE);
            request.getRequestDispatcher(UrlUtils.getCommandUrl(CommandEnum.SHOW_ERROR_PAGE.name())).forward(request, response);
            return;
        }
        String userLoginToUnblock = request.getParameter("user_login");
        User userToUnblock = daoFactory.getUserDAO().get(userLoginToUnblock);

        if (userToUnblock == null || userToUnblock.getRole() == Roles.ADMINISTRATOR) {
            request.setAttribute("errorMessage", CANT_UNBLOCK);
            request.getRequestDispatcher(UrlUtils.getCommandUrl(CommandEnum.SHOW_ERROR_PAGE.name())).forward(request, response);
            return;
        }

        daoFactory.getUserDAO().unblock(userLoginToUnblock);

        String previousPageCommand = request.getParameter("previousPage");
        response.sendRedirect(UrlUtils.getCommandUrl(previousPageCommand));
    }
}

