package ru.rseu.gorkin.web.commands.participant;

import ru.rseu.gorkin.datalayer.dao.DAOFactory;
import ru.rseu.gorkin.datalayer.dto.CompetitionParticipation;
import ru.rseu.gorkin.web.servlets.FrontController;

import javax.servlet.http.HttpServletRequest;

public class ShowCompetitionParticipationById extends ShowCompetitionParticipation {
    private static final String COMPETITION_PARTICIPATION_ID_PARAM = "id";
    @Override
    public CompetitionParticipation getCompetitionParticipation(HttpServletRequest request) {
        DAOFactory daoFactory = (DAOFactory) request
                .getServletContext()
                .getAttribute(FrontController.DAO_FACTORY_CONTEXT_ATTRIBUTE);

        int id = Integer.parseInt(request.getParameter(COMPETITION_PARTICIPATION_ID_PARAM));
        CompetitionParticipation competitionParticipation = daoFactory.getCompetitionParticipationDAO().get(id);

        return competitionParticipation;
    }
}
