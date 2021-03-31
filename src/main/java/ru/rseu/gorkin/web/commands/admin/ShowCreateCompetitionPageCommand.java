package ru.rseu.gorkin.web.commands.admin;

import ru.rseu.gorkin.datalayer.dao.DAOFactory;
import ru.rseu.gorkin.datalayer.db.oracle.StrategyAdapters;
import ru.rseu.gorkin.datalayer.dto.Roles;
import ru.rseu.gorkin.datalayer.dto.Statuses;
import ru.rseu.gorkin.datalayer.dto.User;
import ru.rseu.gorkin.resources.utils.ConfigurationManagers;
import ru.rseu.gorkin.web.servlets.FrontController;
import ru.rseu.gorkin.web.commands.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShowCreateCompetitionPageCommand implements Command {
    private final static String AVAILABLE_EXPERTS_KEY = "experts";
    private final static String AVAILABLE_STRATEGIES_KEY = "strategies";

    private static final String TASK_ATTRIBUTE = "task";
    private static final String END_REGISTRATION_DATE_ATTRIBUTE = "end_registration_date";
    private static final String END_REGISTRATION_TIME_ATTRIBUTE = "end_registration_time";
    private static final String END_SENDING_ANSWERS_DATE_ATTRIBUTE = "end_sending_answers_date";
    private static final String END_SENDING_ANSWERS_TIME_ATTRIBUTE = "end_sending_answers_time";
    private static final String STRATEGY_ATTRIBUTE = "selected_strategy_name";
    private static final String STRATEGY_VALUE_ATTRIBUTE = "strategy_value";
    private static final String EXPERTS_ATTRIBUTE = "selected_experts";
    private List<StrategyAdapters> strategies;

    public ShowCreateCompetitionPageCommand() {
        strategies = new ArrayList<>();
        strategies.add(StrategyAdapters.ALL_POSITIVE);
        strategies.add(StrategyAdapters.MAX_NEGATIVE_MARKS_COUNT);
        strategies.add(StrategyAdapters.PERCENT_POSITIVE_MARKS);
    }


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAOFactory daoFactory = (DAOFactory) request
                .getServletContext()
                .getAttribute(FrontController.DAO_FACTORY_CONTEXT_ATTRIBUTE);

        setBaseValueAttributeIfNotExist(request, TASK_ATTRIBUTE, "");
        setBaseValueAttributeIfNotExist(request, END_REGISTRATION_DATE_ATTRIBUTE, "2021-08-08");
        setBaseValueAttributeIfNotExist(request, END_REGISTRATION_TIME_ATTRIBUTE, "23:59");
        setBaseValueAttributeIfNotExist(request, END_SENDING_ANSWERS_DATE_ATTRIBUTE, "2021-08-08");
        setBaseValueAttributeIfNotExist(request, END_SENDING_ANSWERS_TIME_ATTRIBUTE, "23:59");
        setBaseValueAttributeIfNotExist(request, STRATEGY_ATTRIBUTE, StrategyAdapters.ALL_POSITIVE);
        setBaseValueAttributeIfNotExist(request, STRATEGY_VALUE_ATTRIBUTE, "");

        List<User> activeExperts = daoFactory.getUserDAO().getAllByRole(Roles.EXPERT).stream().filter(user -> user.getStatus() == Statuses.ACTIVE).collect(Collectors.toList());

        request.setAttribute(AVAILABLE_EXPERTS_KEY, activeExperts);
        request.setAttribute(AVAILABLE_STRATEGIES_KEY, strategies);
        request.getRequestDispatcher(ConfigurationManagers.WEB_MANAGER.getProperty("page.admin.createCompetition")).forward(request, response);
    }

    private void setBaseValueAttributeIfNotExist(HttpServletRequest request, String attributeKey, Object defaultValue) {
        if (request.getAttribute(attributeKey) == null) {
            request.setAttribute(attributeKey, defaultValue);
        }
    }


}
