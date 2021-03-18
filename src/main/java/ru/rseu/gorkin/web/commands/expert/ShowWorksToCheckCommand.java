package ru.rseu.gorkin.web.commands.expert;

import ru.rseu.gorkin.resources.utils.ConfigurationManagers;
import ru.rseu.gorkin.web.commands.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowWorksToCheckCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(ConfigurationManagers.WEB_MANAGER.getProperty("page.expert.worksToCheck")).forward(request,response);
    }
}
