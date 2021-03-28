package ru.rseu.gorkin.web.commands.expert;

import ru.rseu.gorkin.datalayer.dao.DAOFactory;
import ru.rseu.gorkin.datalayer.dto.Competition;
import ru.rseu.gorkin.datalayer.dto.CompetitionResultable;
import ru.rseu.gorkin.datalayer.dto.Decision;
import ru.rseu.gorkin.datalayer.dto.Marks;
import ru.rseu.gorkin.resources.utils.ConfigurationManagers;
import ru.rseu.gorkin.web.FrontController;
import ru.rseu.gorkin.web.commands.Command;
import ru.rseu.gorkin.web.commands.CommandEnum;
import ru.rseu.gorkin.web.commands.UrlUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MakeDecisionCommand implements Command {
    private static final String COMPETITION_PARTICIPATION_ID_PARAM = "id";
    private static final String COMPETITION_PARTICIPATION_COMMENT_PARAM = "comment";
    private static final String MARK_PARAM = "mark";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = (DAOFactory) request
                .getServletContext()
                .getAttribute(FrontController.DAO_FACTORY_CONTEXT_ATTRIBUTE);

        int competitionParticipationId = Integer.parseInt(request.getParameter(COMPETITION_PARTICIPATION_ID_PARAM));
        String comment = request.getParameter(COMPETITION_PARTICIPATION_COMMENT_PARAM);
        if (comment == null) {
            comment = "";
        } else {
            comment = comment.trim();
            comment = comment.replaceAll("\n", " ");
        }
        String markString = request.getParameter(MARK_PARAM);
        Marks mark = Marks.valueOf(markString.toUpperCase());
        int userId = (Integer) request.getSession().getAttribute(ConfigurationManagers.WEB_MANAGER.getProperty("session.attribute.id"));

        daoFactory.getDecisionDAO().makeDecision(
                userId,
                competitionParticipationId,
                mark,
                comment
        );
        List<Marks> marksForCurrentParticipation = daoFactory.getDecisionDAO()
                .getDecisionsByCompetitionParticipationId(competitionParticipationId)
                .stream()
                .map(Decision::getMark)
                .collect(Collectors.toList());

        Competition competition = daoFactory
                .getCompetitionParticipationDAO()
                .get(competitionParticipationId)
                .getCompetition();

        int countNotDefinedDecisions = competition.getExperts().size() - marksForCurrentParticipation.size();
        for (int i = 0; i < countNotDefinedDecisions; i++) {
            marksForCurrentParticipation.add(Marks.NOT_DEFINED);
        }


        CompetitionResultable strategy = competition.getEvaluationStrategy();

        Marks expectedMark = strategy.calculateMark(marksForCurrentParticipation);
        if (expectedMark != Marks.NOT_DEFINED) {
            daoFactory.getCompetitionParticipationDAO().setMark(
                    competitionParticipationId,
                    expectedMark
            );
        }

        String url = UrlUtils.getCommandUrl(CommandEnum.SHOW_WORK_TO_CHECK_BY_PARTICIPATION.name().toLowerCase());
        url = String.format("%s&id=%d", url, competitionParticipationId);
        response.sendRedirect(url);
    }
}
