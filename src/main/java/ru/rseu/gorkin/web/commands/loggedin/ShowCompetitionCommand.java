package ru.rseu.gorkin.web.commands.loggedin;

import ru.rseu.gorkin.datalayer.dao.DAOFactory;
import ru.rseu.gorkin.datalayer.dto.Competition;
import ru.rseu.gorkin.datalayer.dto.CompetitionParticipation;
import ru.rseu.gorkin.datalayer.dto.Decision;
import ru.rseu.gorkin.datalayer.dto.Roles;
import ru.rseu.gorkin.resources.utils.ConfigurationManagers;
import ru.rseu.gorkin.web.FrontController;
import ru.rseu.gorkin.web.commands.Command;
import ru.rseu.gorkin.web.commands.CommandEnum;
import ru.rseu.gorkin.web.commands.UrlUtils;
import ru.rseu.gorkin.web.utils.DateTimeUtils;
import ru.rseu.gorkin.web.viewclasses.CompetitionParticipantsView;
import ru.rseu.gorkin.web.viewclasses.CompetitionView;
import ru.rseu.gorkin.web.viewclasses.UrlMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowCompetitionCommand implements Command {
    private static final String ID_PARAMETER = "id";
    private static final String COMPETITION_ATTRIBUTE = "competition_v";
    private static final String PARTICIPANT_STATUS_ATTRIBUTE = "participant_status";
    private static final String ACTIONS_ATTRIBUTE = "actions";
    private static final String PARTICIPANTS_VIEW_ATTRIBUTE = "participants_view";
    private static final String MESSAGE = "message";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CommandEnum> availableActions = new ArrayList<>();

        DAOFactory daoFactory = (DAOFactory) request
                .getServletContext()
                .getAttribute(FrontController.DAO_FACTORY_CONTEXT_ATTRIBUTE);
        int id = Integer.parseInt(request.getParameter(ID_PARAMETER));
        Competition competition = daoFactory.getCompetitionDAO().getById(id);

        Roles role = (Roles) request.getSession().getAttribute(ConfigurationManagers.WEB_MANAGER.getProperty("session.attribute.role"));
        int userId = (Integer) request.getSession().getAttribute(ConfigurationManagers.WEB_MANAGER.getProperty("session.attribute.id"));

        boolean isActual = new DateTimeUtils().isInFuture(competition.getEndRegistrationDate());

        if (isActual) {
            if (role == Roles.ADMINISTRATOR) {
                addAdministratorActions(availableActions);
            }
            if (role == Roles.PARTICIPANT) {
                boolean isParticipantOfCompetition = daoFactory.getCompetitionParticipationDAO().isParticipate(id, userId);
                if (!isParticipantOfCompetition) {
                    addParticipateActions(availableActions);
                } else {
                    String url = String.format("%s&userId=%d&competitionId=%d",
                            UrlUtils.getCommandUrl(CommandEnum.SHOW_COMPETITION_PARTICIPATION_UCID.name().toLowerCase()),
                            userId,
                            competition.getId());
                    request.setAttribute(MESSAGE, new UrlMessage("Вы являетесь участником", url));
                }
            }
        }
        CompetitionParticipantsView competitionParticipantsView = getParticipantsViewInfo(daoFactory, competition);
        CompetitionView competitionView = CompetitionView.createFrom(competition);

        request.setAttribute(COMPETITION_ATTRIBUTE, competitionView);
        request.setAttribute(PARTICIPANTS_VIEW_ATTRIBUTE, competitionParticipantsView);
        request.setAttribute(ACTIONS_ATTRIBUTE, availableActions);
        request.getRequestDispatcher(ConfigurationManagers.WEB_MANAGER.getProperty("page.competition")).forward(request, response);
    }

    private void addAdministratorActions(List<CommandEnum> availableActions) {
        availableActions.add(CommandEnum.SHOW_CHANGE_COMPETITION_COMMAND);
    }

    private void addParticipateActions(List<CommandEnum> availableActions) {
        availableActions.add(CommandEnum.PARTICIPATE);
    }

    private CompetitionParticipantsView getParticipantsViewInfo(DAOFactory daoFactory, Competition competition) {
        List<CompetitionParticipation> competitionParticipations = daoFactory.getCompetitionParticipationDAO().getByCompetitionId(competition.getId());
        List<Decision> decisions = daoFactory.getDecisionDAO().getDecisionsByCompetitionId(competition.getId());
        return CompetitionParticipantsView.createOf(competition, competitionParticipations, decisions);
    }
}
