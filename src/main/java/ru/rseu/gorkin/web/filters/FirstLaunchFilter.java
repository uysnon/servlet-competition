package ru.rseu.gorkin.web.filters;


import ru.rseu.gorkin.resources.utils.ConfigurationManagers;
import ru.rseu.gorkin.web.commands.CommandEnum;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
                CommandEnum.SHOW_CREATE_COMPETITION_PAGE

        ).collect(Collectors.toList());

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        Object login = httpServletRequest.getSession().getAttribute(ConfigurationManagers.WEB_MANAGER.getProperty("session.attribute.login"));
        if (login != null){
            Object firstLaunch =   httpServletRequest.getSession().getAttribute(ConfigurationManagers.WEB_MANAGER.getProperty("session.attribute.firstLaunch"));
            if (firstLaunch == null){
                httpServletRequest.getSession().setAttribute(ConfigurationManagers.WEB_MANAGER.getProperty("session.attribute.firstLaunch"), true);
                httpServletRequest.getSession().setAttribute(ConfigurationManagers.WEB_MANAGER.getProperty("session.attribute.navigationCommands"), navigationCommands);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
