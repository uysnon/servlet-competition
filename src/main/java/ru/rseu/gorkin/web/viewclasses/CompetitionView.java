package ru.rseu.gorkin.web.viewclasses;

import ru.rseu.gorkin.datalayer.db.oracle.StrategyAdapters;
import ru.rseu.gorkin.datalayer.dto.Competition;
import ru.rseu.gorkin.datalayer.dto.User;
import ru.rseu.gorkin.web.utils.DateTimeUtils;

import java.util.List;

public class CompetitionView {
    private int id;
    private String evaluationStrategy;
    private String task;
    private String endRegistrationDate;
    private String endSendingAnswerDate;
    private List<User> experts;
    private String status;

    private CompetitionView() {
    }

    public static CompetitionView createFrom(Competition competition){
        CompetitionView competitionView = new CompetitionView();
        DateTimeUtils dateTimeUtils = new DateTimeUtils();
        competitionView.id = competition.getId();
        competitionView.task = competition.getTask();
        competitionView.endRegistrationDate = dateTimeUtils.format(competition.getEndRegistrationDate());
        competitionView.endSendingAnswerDate = dateTimeUtils.format(competition.getEndSendingAnswerDate());
        competitionView.status = dateTimeUtils.calculateCompetitionStatus(competition);
        competitionView.experts = competition.getExperts();
        competitionView.evaluationStrategy = StrategyAdapters.generateDescription(competition.getEvaluationStrategy());
        return competitionView;
    }

    public int getId() {
        return id;
    }

    public String getEvaluationStrategy() {
        return evaluationStrategy;
    }

    public String getTask() {
        return task;
    }

    public String getEndRegistrationDate() {
        return endRegistrationDate;
    }

    public String getEndSendingAnswerDate() {
        return endSendingAnswerDate;
    }

    public List<User> getExperts() {
        return experts;
    }

    public String getStatus() {
        return status;
    }
}
