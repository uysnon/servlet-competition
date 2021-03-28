package ru.rseu.gorkin.web.commands.expert;

import ru.rseu.gorkin.resources.utils.ConfigurationManager;
import ru.rseu.gorkin.resources.utils.ConfigurationManagers;
import ru.rseu.gorkin.web.commands.Command;
import ru.rseu.gorkin.web.viewclasses.CompetitionForExpertView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public  abstract class ShowWorkToCheckCommand implements Command {
    private static final String COMPEITION_FOR_EXPERT_VIEW_ATTRIBUTE = "competition_v";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CompetitionForExpertView competitionView = getCompetitionView(request);
        request.setAttribute(COMPEITION_FOR_EXPERT_VIEW_ATTRIBUTE, competitionView);

        request.getRequestDispatcher(ConfigurationManagers.WEB_MANAGER.getProperty("page.expert.workMakingDecision")).forward(request, response);
    }

    public abstract CompetitionForExpertView getCompetitionView(HttpServletRequest request);
}
