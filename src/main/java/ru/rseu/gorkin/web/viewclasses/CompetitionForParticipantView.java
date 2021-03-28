package ru.rseu.gorkin.web.viewclasses;

import ru.rseu.gorkin.datalayer.dto.CompetitionParticipation;
import ru.rseu.gorkin.datalayer.dto.Marks;
import ru.rseu.gorkin.web.utils.DateTimeUtils;

import java.util.Objects;

public class CompetitionForParticipantView {
    private int competitionId;
    private int participationId;
    private String task;
    private String endSendingAnswerDate;
    private String status;
    private boolean isAnswerHaveSent;

    private CompetitionForParticipantView() {
    }

    public static CompetitionForParticipantView createOf(CompetitionParticipation participation) {
        CompetitionForParticipantView competitionForParticipantView = new CompetitionForParticipantView();
        DateTimeUtils dateTimeUtils = new DateTimeUtils();
        competitionForParticipantView.setCompetitionId(participation.getCompetition().getId());
        competitionForParticipantView.setParticipationId(participation.getId());
        competitionForParticipantView.setTask(participation.getCompetition().getTask());
        competitionForParticipantView.setEndSendingAnswerDate(dateTimeUtils.format(participation.getCompetition().getEndSendingAnswerDate()));

        boolean isAnswerHaveSent = false;
        String answer = participation.getAnswer();
        if (answer != null && !"".equals(answer)) {
            isAnswerHaveSent = true;
        }

        if (!isAnswerHaveSent) {
            if (dateTimeUtils.isInFuture(participation.getCompetition().getEndSendingAnswerDate())) {
                competitionForParticipantView.setStatus("Ожидается отправка ответа");
            } else {
                competitionForParticipantView.setStatus("Ответ не был отправлен в срок");
            }
        } else {
            if (participation.getMark() == Marks.NOT_DEFINED) {
                competitionForParticipantView.setStatus("Ожидается проверка");
            } else {
                if (participation.getMark() == Marks.POSITIVE) {
                    competitionForParticipantView.setStatus("Призер");
                } else {
                    if (participation.getMark() == Marks.NEGATIVE) {
                        competitionForParticipantView.setStatus("Нет призового места");
                    }
                }
            }
        }
        return competitionForParticipantView;
    }

    public int getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }

    public int getParticipationId() {
        return participationId;
    }

    public void setParticipationId(int participationId) {
        this.participationId = participationId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getEndSendingAnswerDate() {
        return endSendingAnswerDate;
    }

    public void setEndSendingAnswerDate(String endSendingAnswerDate) {
        this.endSendingAnswerDate = endSendingAnswerDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isAnswerHaveSent() {
        return isAnswerHaveSent;
    }

    public void setAnswerHaveSent(boolean answerHaveSent) {
        isAnswerHaveSent = answerHaveSent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompetitionForParticipantView)) return false;
        CompetitionForParticipantView that = (CompetitionForParticipantView) o;
        return competitionId == that.competitionId &&
                participationId == that.participationId &&
                isAnswerHaveSent == that.isAnswerHaveSent &&
                Objects.equals(task, that.task) &&
                Objects.equals(endSendingAnswerDate, that.endSendingAnswerDate) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(competitionId, participationId, task, endSendingAnswerDate, status, isAnswerHaveSent);
    }

    @Override
    public String
    toString() {
        return "CompetitionForParticipantView{" +
                "competitionId=" + competitionId +
                ", participationId=" + participationId +
                ", task='" + task + '\'' +
                ", endSendingAnswerDate='" + endSendingAnswerDate + '\'' +
                ", status='" + status + '\'' +
                ", isAnswerHaveSent=" + isAnswerHaveSent +
                '}';
    }
}
