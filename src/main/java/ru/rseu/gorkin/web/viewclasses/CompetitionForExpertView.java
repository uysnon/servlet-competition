package ru.rseu.gorkin.web.viewclasses;

import ru.rseu.gorkin.datalayer.dto.CompetitionParticipation;
import ru.rseu.gorkin.datalayer.dto.Decision;
import ru.rseu.gorkin.datalayer.dto.Marks;

import java.util.Objects;

public class CompetitionForExpertView {
    private int competitionId;
    private int decisionId;
    private int participationId;
    private String participantName;
    private String task;
    private String answer;
    private String comment;
    private boolean isChecked;
    private String checkStatus;

    private CompetitionForExpertView() {
    }

    public static CompetitionForExpertView createOf(CompetitionParticipation competitionParticipation){
        CompetitionForExpertView view = new CompetitionForExpertView();
        view.setCompetitionId(competitionParticipation.getCompetition().getId());
        view.setTask(competitionParticipation.getCompetition().getTask());
        view.setParticipationId(competitionParticipation.getId());
        view.setParticipantName(competitionParticipation.getParticipant().getName());
        view.setChecked(false);
        view.setComment("");
        view.setCheckStatus("Не проверено");
        view.setDecisionId(-1);
        view.setAnswer(competitionParticipation.getAnswer());
        return view;
    }

    public static CompetitionForExpertView createOf(Decision decision){
        CompetitionForExpertView view = createOf(decision.getCompetitionParticipation());
        view.setChecked(true);
        view.setDecisionId(decision.getId());
        view.setComment(decision.getComment());
        Marks mark = decision.getMark();
        String checkStatus = "" ;
        if (mark == Marks.POSITIVE){
            checkStatus = "Одобрено";
        } else {
            checkStatus = "Забраковано";
        }
        view.setCheckStatus(checkStatus);

        return  view;
    }

    public int getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }

    public int getDecisionId() {
        return decisionId;
    }

    public void setDecisionId(int decisionId) {
        this.decisionId = decisionId;
    }

    public int getParticipationId() {
        return participationId;
    }

    public void setParticipationId(int participationId) {
        this.participationId = participationId;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompetitionForExpertView)) return false;
        CompetitionForExpertView that = (CompetitionForExpertView) o;
        return competitionId == that.competitionId &&
                decisionId == that.decisionId &&
                participationId == that.participationId &&
                isChecked == that.isChecked &&
                Objects.equals(participantName, that.participantName) &&
                Objects.equals(task, that.task) &&
                Objects.equals(answer, that.answer) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(checkStatus, that.checkStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(competitionId, decisionId, participationId, participantName, task, answer, comment, isChecked, checkStatus);
    }

    @Override
    public String toString() {
        return "CompetitionForExpertView{" +
                "competitionId=" + competitionId +
                ", decisionId=" + decisionId +
                ", participationId=" + participationId +
                ", participantName='" + participantName + '\'' +
                ", task='" + task + '\'' +
                ", answer='" + answer + '\'' +
                ", comment='" + comment + '\'' +
                ", isChecked=" + isChecked +
                ", checkStatus='" + checkStatus + '\'' +
                '}';
    }
}
