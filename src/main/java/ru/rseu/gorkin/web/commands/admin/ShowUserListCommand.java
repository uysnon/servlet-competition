package ru.rseu.gorkin.web.commands.admin;

import ru.rseu.gorkin.datalayer.dao.DAOFactory;
import ru.rseu.gorkin.datalayer.dto.Roles;
import ru.rseu.gorkin.datalayer.dto.User;
import ru.rseu.gorkin.resources.utils.ConfigurationManagers;
import ru.rseu.gorkin.web.FrontController;
import ru.rseu.gorkin.web.commands.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowUserListCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = (DAOFactory) request
                .getServletContext()
                .getAttribute(FrontController.DAO_FACTORY_CONTEXT_ATTRIBUTE);

        List<User> admin_users = daoFactory.getUserDAO().getAllByRole(Roles.ADMINISTRATOR);
        List<User> expert_users = daoFactory.getUserDAO().getAllByRole(Roles.EXPERT);
        List<User> participant_users = daoFactory.getUserDAO().getAllByRole(Roles.PARTICIPANT);

        request.setAttribute("admin_users", admin_users);
        request.setAttribute("expert_users", expert_users);
        request.setAttribute("participant_users", participant_users);

        request.getRequestDispatcher(ConfigurationManagers.WEB_MANAGER.getProperty("page.admin.userList")).forward(request, response);
    }
}
