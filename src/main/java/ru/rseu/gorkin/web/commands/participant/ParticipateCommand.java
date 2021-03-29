package ru.rseu.gorkin.web.commands.participant;

import ru.rseu.gorkin.datalayer.dao.DAOFactory;
import ru.rseu.gorkin.resources.utils.ConfigurationManagers;
import ru.rseu.gorkin.web.servlets.FrontController;
import ru.rseu.gorkin.web.commands.Command;
import ru.rseu.gorkin.web.commands.CommandEnum;
import ru.rseu.gorkin.web.commands.UrlUtils;
import ru.rseu.gorkin.web.utils.DateTimeUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ParticipateCommand implements Command {
    private static final String COMPETITION_ID_PARAM = "competition_id";
    private static final String SUCCESS_MESSAGE_ATTRIBUTE = "success_message";
    private static final String ERROR_MESSAGE_ATTRIBUTE = "error_message";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = (DAOFactory) request
                .getServletContext()
                .getAttribute(FrontController.DAO_FACTORY_CONTEXT_ATTRIBUTE);
        int competitionId = Integer.parseInt(request.getParameter(COMPETITION_ID_PARAM));
        int userId = (Integer) request.getSession().getAttribute(ConfigurationManagers.WEB_MANAGER.getProperty("session.attribute.id"));
        boolean isParticipate = daoFactory.getCompetitionParticipationDAO().isParticipate(competitionId, userId);
        String urlToReturn =
                String.format("%s&id=%d", UrlUtils.getCommandUrl(CommandEnum.SHOW_COMPETITION.name()), competitionId);
        DateTimeUtils dateTimeUtils = new DateTimeUtils();
        boolean isAnswerSendDateInFuture = dateTimeUtils.isInFuture(
          daoFactory.getCompetitionDAO().getById(competitionId).getEndSendingAnswerDate());
        if (!isParticipate && isAnswerSendDateInFuture) {
            daoFactory.getCompetitionParticipationDAO().participate(competitionId, userId);
        }
//        request.setAttribute(SUCCESS_MESSAGE_ATTRIBUTE,"Теперь вы участвуете в конкурсе!");
        response.sendRedirect(urlToReturn);
    }


}
