package ru.rseu.gorkin.web.commands.participant;

import ru.rseu.gorkin.datalayer.dao.DAOFactory;
import ru.rseu.gorkin.datalayer.dto.CompetitionParticipation;
import ru.rseu.gorkin.resources.utils.ConfigurationManagers;
import ru.rseu.gorkin.web.FrontController;
import ru.rseu.gorkin.web.commands.Command;
import ru.rseu.gorkin.web.utils.DateTimeUtils;
import ru.rseu.gorkin.web.viewclasses.CompetitionForParticipantView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ShowUserCompetitions implements Command {
    private static final String ACTUAL_COMPETITION_VIEWS_ATTRIBUTE = "actual_competitions_v";
    private static final String NON_ACTUAL_COMPETITION_VIEWS_ATTRIBUTE = "non_actual_competitions_v";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = (DAOFactory) request
                .getServletContext()
                .getAttribute(FrontController.DAO_FACTORY_CONTEXT_ATTRIBUTE);

        int userId = (Integer) request.getSession().getAttribute(ConfigurationManagers.WEB_MANAGER.getProperty("session.attribute.id"));
        List<CompetitionParticipation> userCompetitionParticipations =
                daoFactory.getCompetitionParticipationDAO().getByParticipantId(
                        userId
                );
        List<CompetitionParticipation> actualParticipations = getActualCompetitions(userCompetitionParticipations);
        List<CompetitionParticipation> nonActualParticipations = getNonActualCompetitions(userCompetitionParticipations);
        List<CompetitionForParticipantView> actualCompetitionViews =
                actualParticipations
                        .stream()
                        .map(participation -> CompetitionForParticipantView.createOf(participation))
                        .collect(Collectors.toList());

        List<CompetitionForParticipantView> nonActualCompetitionViews =
                nonActualParticipations
                        .stream()
                        .map(participation -> CompetitionForParticipantView.createOf(participation))
                        .collect(Collectors.toList());

        request.setAttribute(ACTUAL_COMPETITION_VIEWS_ATTRIBUTE, actualCompetitionViews);
        request.setAttribute(NON_ACTUAL_COMPETITION_VIEWS_ATTRIBUTE, nonActualCompetitionViews);

        request.getRequestDispatcher(ConfigurationManagers.WEB_MANAGER.getProperty("page.participant.registeredCompetitions")).forward(request, response);
    }


    List<CompetitionParticipation> getActualCompetitions(List<CompetitionParticipation> allCompetitionParticipations) {
        DateTimeUtils dateTimeUtils = new DateTimeUtils();
        return allCompetitionParticipations.stream()
                .filter(isActual())
                .collect(Collectors.toList());
    }

    List<CompetitionParticipation> getNonActualCompetitions(List<CompetitionParticipation> allCompetitionParticipations) {
        DateTimeUtils dateTimeUtils = new DateTimeUtils();
        return allCompetitionParticipations.stream()
                .filter(isActual().negate())
                .collect(Collectors.toList());
    }

    Predicate<CompetitionParticipation> isActual() {
        DateTimeUtils dateTimeUtils = new DateTimeUtils();
        return competitionParticipation ->
                competitionParticipation.getAnswer() == null
                        && dateTimeUtils.isInFuture(competitionParticipation.getCompetition().getEndSendingAnswerDate());
    }
}
