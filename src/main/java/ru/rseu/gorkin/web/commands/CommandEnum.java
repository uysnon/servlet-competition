package ru.rseu.gorkin.web.commands;

import ru.rseu.gorkin.web.commands.admin.*;
import ru.rseu.gorkin.web.commands.expert.MakeDecisionCommand;
import ru.rseu.gorkin.web.commands.expert.ShowWorkToCheckCommand;
import ru.rseu.gorkin.web.commands.expert.ShowWorksToCheckCommand;
import ru.rseu.gorkin.web.commands.guest.*;
import ru.rseu.gorkin.web.commands.loggedin.*;
import ru.rseu.gorkin.web.commands.participant.ParticipateCommand;
import ru.rseu.gorkin.web.commands.participant.ShowCompetitionParticipateByUserAndCompetitionIds;
import ru.rseu.gorkin.web.commands.participant.ShowCompetitionParticipationById;
import ru.rseu.gorkin.web.commands.participant.ShowUserCompetitions;

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
    SHOW_EXPERT_DECISION(new ShowExpertDecisionCommand()),
    MAKE_DECISION(new MakeDecisionCommand()),
    SHOW_WORK_TO_CHECK(new ShowWorkToCheckCommand()),
    PARTICIPATE(new ParticipateCommand(), "Участвовать"),
    SHOW_USER_COMPETITIONS(new ShowUserCompetitions(), "Мои конкурсы"),
    SHOW_CHANGE_COMPETITION_COMMAND(new ShowChangeCompetitionCommand(),"Изменить"),
    CHANGE_COMPETITION_COMMAND(new ChangeCompetitionCommand()),
    SHOW_COMPETITION_PARTICIPATION_ID(new ShowCompetitionParticipationById()),
    SHOW_COMPETITION_PARTICIPATION_UCID(new ShowCompetitionParticipateByUserAndCompetitionIds());

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
