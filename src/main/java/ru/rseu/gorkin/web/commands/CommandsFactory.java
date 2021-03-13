package ru.rseu.gorkin.web.commands;

import javax.servlet.http.HttpServletRequest;

public class CommandsFactory {
    private static final String REQUEST_COMMAND_PARAMETER = "command";
    public Command defineCommand(HttpServletRequest request) {
        Command createdCommand = new WelcomeCommand();
        String action = request.getParameter(REQUEST_COMMAND_PARAMETER);
        if (action == null || action.isEmpty()) {
            return createdCommand;
        }
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            createdCommand = currentEnum.getCommand();
        } catch (IllegalArgumentException e) {
        }
        return createdCommand;
    }
}
