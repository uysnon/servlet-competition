package ru.rseu.gorkin.web.commands;

public enum CommandEnum {
    WELCOME(new WelcomeCommand()),
    SHOW_LOGIN_PAGE(new ShowLoginPageCommand()),
    LOGIN(new LoginCommand()),
    SHOW_USERS_LIST(new ShowUsersListCommand()),
    SHOW_ALL_COMPETITIONS(new ShowAllCompetitionsCommand()),
    SHOW_WORKS_TO_CHECK(new ShowWorksToCheckCommand());

    private Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
