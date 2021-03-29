package ru.rseu.gorkin.web.filters;


import ru.rseu.gorkin.datalayer.dto.Roles;
import ru.rseu.gorkin.resources.utils.ConfigurationManagers;
import ru.rseu.gorkin.web.commands.Command;
import ru.rseu.gorkin.web.commands.CommandEnum;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FirstLaunchFilter implements Filter {
    private List<CommandEnum> navigationCommands;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        navigationCommands = Stream.of(
                CommandEnum.SHOW_ALL_COMPETITIONS,
                CommandEnum.SHOW_CREATE_ACCOUNT_PAGE,
                CommandEnum.SHOW_USER_LIST,
                CommandEnum.SHOW_CREATE_COMPETITION_PAGE,
                CommandEnum.SHOW_USER_COMPETITIONS,
                CommandEnum.SHOW_WORKS_TO_CHECK,
                CommandEnum.SHOW_EDIT_ACCOUNT_BY_USER
        ).collect(Collectors.toList());

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        Object role = httpServletRequest.getSession().getAttribute(ConfigurationManagers.WEB_MANAGER.getProperty("session.attribute.role"));
        if (role != null) {
            Object firstLaunch = httpServletRequest.getSession().getAttribute(ConfigurationManagers.WEB_MANAGER.getProperty("session.attribute.firstLaunch"));
            if (firstLaunch == null) {
                httpServletRequest.getSession().setAttribute(ConfigurationManagers.WEB_MANAGER.getProperty("session.attribute.firstLaunch"), true);
                httpServletRequest.getSession().setAttribute(ConfigurationManagers.WEB_MANAGER.getProperty("session.attribute.navigationCommands"), filter((Roles) role));
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public List<CommandEnum> filter(Roles role) {
        List<CommandEnum> availableCommands = new ArrayList<>(UserRightsFilter.loggedInUserActions);
        if (role == Roles.PARTICIPANT) {
            availableCommands.addAll(UserRightsFilter.participantActions);
        }
        if (role == Roles.EXPERT) {
            availableCommands.addAll(UserRightsFilter.expertActions);
        }
        if (role == Roles.ADMINISTRATOR) {
            availableCommands.addAll(UserRightsFilter.adminActions);
        }
        return navigationCommands.stream()
                .filter(element -> availableCommands.contains(element))
                .collect(Collectors.toList());
    }

    @Override
    public void destroy() {

    }
}
