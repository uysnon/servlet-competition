package ru.rseu.gorkin.web.commands.expert;

import ru.rseu.gorkin.datalayer.dao.DAOFactory;
import ru.rseu.gorkin.web.servlets.FrontController;
import ru.rseu.gorkin.web.viewclasses.CompetitionForExpertView;

import javax.servlet.http.HttpServletRequest;

public class ShowWorkToCheckByDecisionCommand extends ShowWorkToCheckCommand {
    private static final String DECISION_ID_PARAM = "id";

    @Override
    public CompetitionForExpertView getCompetitionView(HttpServletRequest request) {
        DAOFactory daoFactory = (DAOFactory) request
                .getServletContext()
                .getAttribute(FrontController.DAO_FACTORY_CONTEXT_ATTRIBUTE);

        int decisionId = Integer.parseInt(request.getParameter(DECISION_ID_PARAM));

        return CompetitionForExpertView.createOf(
                daoFactory.getDecisionDAO().get(decisionId)
        );
    }
}
