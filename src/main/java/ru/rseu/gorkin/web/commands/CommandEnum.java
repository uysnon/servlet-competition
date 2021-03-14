package ru.rseu.gorkin.web.commands;

public enum CommandEnum {
    WELCOME(new WelcomeCommand()),
    SHOW_LOGIN_PAGE(new ShowLoginPageCommand()),
    LOGIN(new LoginCommand()),
    SHOW_USER_LIST(new ShowUserListCommand()),
    SHOW_ALL_COMPETITIONS(new ShowAllCompetitionsCommand()),
    SHOW_WORKS_TO_CHECK(new ShowWorksToCheckCommand()),
    LOGOUT(new LogoutCommand()),
    BLOCK(new BlockCommand()),
    UNBLOCK(new UnblockCommand()),
    SHOW_ERROR_PAGE(new ShowErrorPageCommand()),
    DELETE(new DeleteCommand());

    private Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
