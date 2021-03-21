package ru.rseu.gorkin.web.commands;

import ru.rseu.gorkin.web.commands.admin.*;
import ru.rseu.gorkin.web.commands.expert.ShowWorksToCheckCommand;
import ru.rseu.gorkin.web.commands.guest.*;
import ru.rseu.gorkin.web.commands.loggedin.DeleteCommand;
import ru.rseu.gorkin.web.commands.loggedin.LogoutCommand;
import ru.rseu.gorkin.web.commands.loggedin.ShowAllCompetitionsCommand;
import ru.rseu.gorkin.web.commands.loggedin.ShowCompetitionCommand;

public enum CommandEnum {
    WELCOME(new WelcomeCommand()),
    SHOW_LOGIN_PAGE(new ShowLoginPageCommand()),
    LOGIN(new LoginCommand()),
    SHOW_USER_LIST(new ShowUserListCommand(), "Список пользователей"),
    SHOW_ALL_COMPETITIONS(new ShowAllCompetitionsCommand(), "Научные конкурсы"),
    SHOW_WORKS_TO_CHECK(new ShowWorksToCheckCommand(), "Работы к проверке"),
    LOGOUT(new LogoutCommand()),
    BLOCK(new BlockCommand()),
    UNBLOCK(new UnblockCommand()),
    SHOW_ERROR_PAGE(new ShowErrorPageCommand()),
    DELETE(new DeleteCommand()),
    SHOW_REGISTRATION_PAGE(new ShowRegistrationPageCommand()),
    REGISTER(new RegisterCommand()),
    SHOW_CREATE_ACCOUNT_PAGE(new ShowCreateAccountPageCommand(), "Создать учетную запись"),
    CREATE_ACCOUNT(new CreateAccountCommand()),
    SHOW_COMPETITION_COMMAND(new ShowCompetitionCommand()),
    CREATE_COMPETITION(new CreateCompetitionCommand()),
    SHOW_CREATE_COMPETITION_PAGE(new ShowCreateCompetitionPageCommand(), "Создать конкурс");

    private Command command;
    private String title;

    CommandEnum(Command command) {
        this.command = command;
        this.title = "empty";
    }
    CommandEnum(Command command, String title){
        this.command = command;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Command getCommand() {
        return command;
    }
}
