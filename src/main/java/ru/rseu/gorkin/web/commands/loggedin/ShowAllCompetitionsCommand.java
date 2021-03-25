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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ShowAllCompetitionsCommand implements Command {
    public static String COMPETITIONS_ATTRIBUTE = "competitions_v";

    public ShowAllCompetitionsCommand() {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = (DAOFactory) request
                .getServletContext()
                .getAttribute(FrontController.DAO_FACTORY_CONTEXT_ATTRIBUTE);

        List<Competition> competitions = daoFactory.getCompetitionDAO().getAll();
        competitions.sort(Comparator.comparing(Competition::getEndRegistrationDate).reversed());
        List<CompetitionView> competitionViews = competitions.stream().map(CompetitionView::createFrom).collect(Collectors.toList());
        request.setAttribute(COMPETITIONS_ATTRIBUTE, competitionViews);
        request.getRequestDispatcher(ConfigurationManagers.WEB_MANAGER.getProperty("page.allCompetitions")).forward(request,response);

    }
}
