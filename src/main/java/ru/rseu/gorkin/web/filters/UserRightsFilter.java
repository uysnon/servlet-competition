package ru.rseu.gorkin.web.filters;


import ru.rseu.gorkin.datalayer.dto.Roles;
import ru.rseu.gorkin.resources.utils.ConfigurationManagers;
import ru.rseu.gorkin.web.commands.Command;
import ru.rseu.gorkin.web.commands.CommandEnum;
import ru.rseu.gorkin.web.commands.UrlUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserRightsFilter implements Filter {
    private static final String REQUEST_COMMAND_PARAMETER = "command";

    public static List<CommandEnum> participantActions;
    public static List<CommandEnum> expertActions;
    public static List<CommandEnum> adminActions;
    public static List<CommandEnum> loggedInUserActions;
    public static List<CommandEnum> guestActions;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        loggedInUserActions = Stream.of(
                CommandEnum.LOGOUT,
                CommandEnum.SHOW_ALL_COMPETITIONS,
                CommandEnum.DELETE,
                CommandEnum.SHOW_COMPETITION)
                .collect(Collectors.toList());

        participantActions = Stream.of(
                CommandEnum.SHOW_USER_COMPETITIONS,
                CommandEnum.PARTICIPATE,
                CommandEnum.SHOW_COMPETITION_PARTICIPATION_ID,
                CommandEnum.SHOW_COMPETITION_PARTICIPATION_UCID
        ).collect(Collectors.toList());

        adminActions = Stream.of(
                CommandEnum.SHOW_USER_LIST,
                CommandEnum.BLOCK,
                CommandEnum.CREATE_ACCOUNT,
                CommandEnum.SHOW_CREATE_ACCOUNT_PAGE,
                CommandEnum.SHOW_CREATE_COMPETITION_PAGE,
                CommandEnum.CREATE_COMPETITION,
                CommandEnum.SHOW_CHANGE_COMPETITION_COMMAND,
                CommandEnum.CHANGE_COMPETITION_COMMAND)
                .collect(Collectors.toList());

        expertActions = Stream.of(
                CommandEnum.SHOW_WORKS_TO_CHECK,
                CommandEnum.SHOW_WORK_TO_CHECK_BY_DECISION,
                CommandEnum.SHOW_WORK_TO_CHECK_BY_PARTICIPATION,
                CommandEnum.MAKE_DECISION)
                .collect(Collectors.toList());

        guestActions = Stream.of(CommandEnum.SHOW_LOGIN_PAGE)
                .collect(Collectors.toList());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        Object userRoleObject = httpServletRequest.getSession().getAttribute(ConfigurationManagers.WEB_MANAGER.getProperty("session.attribute.role"));
        List<CommandEnum> deprecatedActionsList = new ArrayList<>();
        boolean isUserRightsNormal = true;
        if (userRoleObject == null || (Roles) userRoleObject == Roles.GUEST) {
            deprecatedActionsList.addAll(expertActions);
            deprecatedActionsList.addAll(participantActions);
            deprecatedActionsList.addAll(adminActions);
            deprecatedActionsList.addAll(loggedInUserActions);
            if (!isUserHaveRights(httpServletRequest, deprecatedActionsList)) {
                httpServletResponse.sendRedirect(UrlUtils.getCommandUrl("SHOW_LOGIN_PAGE"));
                isUserRightsNormal = false;
            }
        } else {
            Roles userRole = (Roles) userRoleObject;
            deprecatedActionsList.addAll(guestActions);
            switch (userRole) {
                case ADMINISTRATOR: {
                    deprecatedActionsList.addAll(expertActions);
                    deprecatedActionsList.addAll(participantActions);
                    break;
                }
                case EXPERT: {
                    deprecatedActionsList.addAll(adminActions);
                    deprecatedActionsList.addAll(participantActions);
                    break;
                }
                case PARTICIPANT: {
                    deprecatedActionsList.addAll(expertActions);
                    deprecatedActionsList.addAll(adminActions);
                    break;
                }
            }
            if (!isUserHaveRights(httpServletRequest, deprecatedActionsList)) {
                httpServletRequest.getRequestDispatcher(UrlUtils.getCommandUrl("show_all_competitions")).forward(httpServletRequest, httpServletResponse);
                isUserRightsNormal = false;
            }

        }
        if (isUserRightsNormal) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private boolean isUserHaveRights(HttpServletRequest request, List<CommandEnum> deprecatedActions) {
        String action = request.getParameter(REQUEST_COMMAND_PARAMETER);
        if (action == null || action.isEmpty()) {
            return true;
        }
        for (CommandEnum deprecatedAction : deprecatedActions) {
            if (action.equalsIgnoreCase(deprecatedAction.name())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void destroy() {

    }
}
