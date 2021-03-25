package ru.rseu.gorkin.web.commands.loggedin;

import ru.rseu.gorkin.web.commands.Command;
import ru.rseu.gorkin.web.commands.CommandEnum;
import ru.rseu.gorkin.web.commands.UrlUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect(UrlUtils.getCommandUrl(CommandEnum.SHOW_LOGIN_PAGE.name().toLowerCase()));
    }
}
