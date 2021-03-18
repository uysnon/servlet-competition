package ru.rseu.gorkin.web.commands;

import ru.rseu.gorkin.web.commands.admin.*;
import ru.rseu.gorkin.web.commands.expert.ShowWorksToCheckCommand;
import ru.rseu.gorkin.web.commands.guest.*;
import ru.rseu.gorkin.web.commands.loggedin.DeleteCommand;
import ru.rseu.gorkin.web.commands.loggedin.LogoutCommand;
import ru.rseu.gorkin.web.commands.loggedin.ShowAllCompetitionsCommand;

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
    DELETE(new DeleteCommand()),
    SHOW_REGISTRATION_PAGE(new ShowRegistrationPageCommand()),
    REGISTER(new RegisterCommand()),
    SHOW_CREATE_ACCOUNT_PAGE(new ShowCreateAccountPageCommand()),
    CREATE_ACCOUNT(new CreateAccountCommand());

    private Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
