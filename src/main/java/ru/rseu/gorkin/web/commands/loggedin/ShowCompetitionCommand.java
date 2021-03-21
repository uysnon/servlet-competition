package ru.rseu.gorkin.web.commands.loggedin;

import ru.rseu.gorkin.datalayer.dao.DAOFactory;
import ru.rseu.gorkin.datalayer.dto.Competition;
import ru.rseu.gorkin.resources.utils.ConfigurationManagers;
import ru.rseu.gorkin.web.FrontController;
import ru.rseu.gorkin.web.commands.Command;
import ru.rseu.gorkin.web.viewclasses.CompetitionView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowCompetitionCommand implements Command {
    private static final String REQUEST_ID_PARAMETER = "id";
    private static final String REQUEST_COMPETITION_ATTRIBUTE = "competition_v";


    public ShowCompetitionCommand() {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = (DAOFactory) request
                .getServletContext()
                .getAttribute(FrontController.DAO_FACTORY_CONTEXT_ATTRIBUTE);
        int id = Integer.parseInt(request.getParameter(REQUEST_ID_PARAMETER));
        Competition competition = daoFactory.getCompetitionDAO().getById(id);

        request.setAttribute(REQUEST_COMPETITION_ATTRIBUTE, CompetitionView.createOf(competition));

        request.getRequestDispatcher(ConfigurationManagers.WEB_MANAGER.getProperty("page.competition")).forward(request, response);
    }
}
