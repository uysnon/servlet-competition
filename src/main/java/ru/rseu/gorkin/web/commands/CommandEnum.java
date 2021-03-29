package ru.rseu.gorkin.web.commands;

import ru.rseu.gorkin.web.commands.admin.*;
import ru.rseu.gorkin.web.commands.expert.*;
import ru.rseu.gorkin.web.commands.guest.*;
import ru.rseu.gorkin.web.commands.loggedin.*;
import ru.rseu.gorkin.web.commands.participant.*;

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
    SHOW_COMPETITION(new ShowCompetitionCommand()),
    CREATE_COMPETITION(new CreateCompetitionCommand()),
    SHOW_CREATE_COMPETITION_PAGE(new ShowCreateCompetitionPageCommand(), "Создать конкурс"),
    MAKE_DECISION(new MakeDecisionCommand()),
    SHOW_WORK_TO_CHECK_BY_PARTICIPATION(new ShowWorkToCheckByParticipationCommand()),
    SHOW_WORK_TO_CHECK_BY_DECISION(new ShowWorkToCheckByDecisionCommand()),
    PARTICIPATE(new ParticipateCommand(), "Участвовать"),
    SHOW_USER_COMPETITIONS(new ShowUserCompetitions(), "Мои конкурсы"),
    SHOW_CHANGE_COMPETITION_COMMAND(new ShowChangeCompetitionCommand(), "Изменить"),
    CHANGE_COMPETITION_COMMAND(new ChangeCompetitionCommand()),
    SHOW_COMPETITION_PARTICIPATION_ID(new ShowCompetitionParticipationById()),
    SHOW_COMPETITION_PARTICIPATION_UCID(new ShowCompetitionParticipateByUserAndCompetitionIds()),
    SEND_ANSWER(new SendAnswerCommand()),
    SHOW_EDIT_ACCOUNT_BY_USER(new ShowEditAccountByUserPageCommand(), "Учетная запись"),
    EDIT_ACCOUNT_BY_USER(new EditAccountByUserCommand()),
    SHOW_EDIT_ACCOUNT_BY_ADMIN(new ShowEditAccountByAdminPageCommand()),
    EDIT_ACCOUNT_BY_ADMIN(new EditAccountByAdminCommand());


    private Command command;
    private String title;

    CommandEnum(Command command) {
        this.command = command;
        this.title = "empty";
    }

    CommandEnum(Command command, String title) {
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
