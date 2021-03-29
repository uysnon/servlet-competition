package ru.rseu.gorkin.web.commands.expert;

import ru.rseu.gorkin.datalayer.dao.DAOFactory;
import ru.rseu.gorkin.datalayer.dto.CompetitionParticipation;
import ru.rseu.gorkin.datalayer.dto.Decision;
import ru.rseu.gorkin.resources.utils.ConfigurationManagers;
import ru.rseu.gorkin.web.servlets.FrontController;
import ru.rseu.gorkin.web.commands.Command;
import ru.rseu.gorkin.web.viewclasses.CompetitionForExpertView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ShowWorksToCheckCommand implements Command {
    private static final String WORKS_TO_CHECK_ATTRIBUTE = "competitions_to_check_v";
    private static final String HISTORY_WORKS_ATTRIBUTE = "competitions_history_v";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = (DAOFactory) request
                .getServletContext()
                .getAttribute(FrontController.DAO_FACTORY_CONTEXT_ATTRIBUTE);

        int expertId = (Integer) request.getSession().getAttribute(ConfigurationManagers.WEB_MANAGER.getProperty("session.attribute.id"));

        List<CompetitionParticipation> participationsToCheck = daoFactory.getCompetitionParticipationDAO().getVerificationRequiredParticipations(expertId);
        List<Decision> decisionsHistory = daoFactory.getDecisionDAO().getExpertDecisions(expertId);

        List<CompetitionForExpertView> toCheckCompetitionViews =
                participationsToCheck
                        .stream()
                        .map(participation -> CompetitionForExpertView.createOf(participation))
                        .collect(Collectors.toList());

        List<CompetitionForExpertView> historyCompetitionViews =
                decisionsHistory
                        .stream()
                        .map(decision -> CompetitionForExpertView.createOf(decision))
                        .collect(Collectors.toList());

        request.setAttribute(WORKS_TO_CHECK_ATTRIBUTE, toCheckCompetitionViews);
        request.setAttribute(HISTORY_WORKS_ATTRIBUTE, historyCompetitionViews);

        request.getRequestDispatcher(ConfigurationManagers.WEB_MANAGER.getProperty("page.expert.worksToCheck")).forward(request, response);
    }
}
