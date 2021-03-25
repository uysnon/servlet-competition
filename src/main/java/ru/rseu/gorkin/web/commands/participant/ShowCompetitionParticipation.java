package ru.rseu.gorkin.web.commands.participant;

import ru.rseu.gorkin.datalayer.dao.DAOFactory;
import ru.rseu.gorkin.datalayer.dto.*;
import ru.rseu.gorkin.resources.utils.ConfigurationManager;
import ru.rseu.gorkin.resources.utils.ConfigurationManagers;
import ru.rseu.gorkin.web.FrontController;
import ru.rseu.gorkin.web.commands.Command;
import ru.rseu.gorkin.web.viewclasses.CompetitionView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ShowCompetitionParticipation implements Command {
    private static final String COMPETITION_VIEW_ATTRIBUTE = "competition_v";
    private static final String COMPETITION_PARTICIPATION_ATTRIBUTE = "competition_participation";
    private static final String DECISIONS_ATTRIBUTE = "decisions";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CompetitionParticipation competitionParticipation = getCompetitionParticipation(request);
        int id = competitionParticipation.getId();
        DAOFactory daoFactory = (DAOFactory) request
                .getServletContext()
                .getAttribute(FrontController.DAO_FACTORY_CONTEXT_ATTRIBUTE);
        List<Decision> decisions = createDecisions(competitionParticipation.getCompetition(), daoFactory.getDecisionDAO().getDecisionsByCompetitionParticipationId(id));

        CompetitionView competitionView = CompetitionView.createFrom(competitionParticipation.getCompetition());

        request.setAttribute(COMPETITION_VIEW_ATTRIBUTE, competitionView);
        request.setAttribute(COMPETITION_PARTICIPATION_ATTRIBUTE, competitionParticipation);
        request.setAttribute(DECISIONS_ATTRIBUTE, decisions);

        request.getRequestDispatcher(ConfigurationManagers.WEB_MANAGER.getProperty("page.participant.competitionParticipation")).forward(request, response);
    }

    private List<Decision> createDecisions(Competition competition, List<Decision> madeDecisions) {
        List<Decision> result = new ArrayList<>(madeDecisions);
        competition.getExperts().forEach(expert -> {
            if (!isExpertDecisionInList(expert, result)) {
                result.add(createEmptyDecision(expert));
            }
        });
        return result;
    }

    private Decision createEmptyDecision(User expert) {
        Decision decision = new Decision();
        decision.setExpert(expert);
        decision.setComment(null);
        decision.setMark(Marks.NOT_DEFINED);
        return decision;
    }

    private boolean isExpertDecisionInList(User expert, List<Decision> decisions) {
        for (Decision decision : decisions) {
            if (decision.getExpert().getId() == expert.getId()) {
                return true;
            }
        }
        return false;
    }

    public abstract CompetitionParticipation getCompetitionParticipation(HttpServletRequest request);
}