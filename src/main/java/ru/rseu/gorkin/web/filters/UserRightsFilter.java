package ru.rseu.gorkin.web.filters;


import ru.rseu.gorkin.datalayer.dto.Roles;
import ru.rseu.gorkin.resources.utils.ConfigurationManagers;
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

    private static List<String> participantActions;
    private static List<String> expertActions;
    private static List<String> adminActions;
    private static List<String> loggedInUserActions;
    private static List<String> guestActions;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        loggedInUserActions = Stream.of("logout", "show_all_competitions", "delete").collect(Collectors.toList());
        participantActions = Stream.of("some_command").collect(Collectors.toList());
        adminActions = Stream.of("show_user_list", "block", "unblock").collect(Collectors.toList());
        expertActions = Stream.of("show_works_to_check").collect(Collectors.toList());
        guestActions = Stream.of("show_login_page").collect(Collectors.toList());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        Object userRoleObject = httpServletRequest.getSession().getAttribute(ConfigurationManagers.WEB_MANAGER.getProperty("session.attribute.role"));
        List<String> deprecatedActionsList = new ArrayList<>();
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

    private boolean isUserHaveRights(HttpServletRequest request, List<String> deprecatedActions) {
        String action = request.getParameter(REQUEST_COMMAND_PARAMETER);
        if (action == null || action.isEmpty()) {
            return true;
        }
        for (String deprecatedAction : deprecatedActions) {
            if (action.equalsIgnoreCase(deprecatedAction)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void destroy() {

    }
}
