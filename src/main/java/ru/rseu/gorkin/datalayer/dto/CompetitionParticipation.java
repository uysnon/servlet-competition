package ru.rseu.gorkin.datalayer.dto;

import java.util.Objects;

public class CompetitionParticipation {
    private int id;
    private Competition competition;
    private User participant;
    private Marks mark;
    private String answer;

    public CompetitionParticipation() {
    }

    public CompetitionParticipation(int id, Competition competition, User participant, Marks mark, String answer) {
        this.id = id;
        this.competition = competition;
        this.participant = participant;
        this.mark = mark;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public User getParticipant() {
        return participant;
    }

    public void setParticipant(User participant) {
        this.participant = participant;
    }

    public Marks getMark() {
        return mark;
    }

    public void setMark(Marks mark) {
        this.mark = mark;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompetitionParticipation)) return false;
        CompetitionParticipation that = (CompetitionParticipation) o;
        return id == that.id &&
                Objects.equals(competition, that.competition) &&
                Objects.equals(participant, that.participant) &&
                mark == that.mark &&
                Objects.equals(answer, that.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, competition, participant, mark, answer);
    }

    @Override
    public String toString() {
        return "CompetitionParticipation{" +
                "id=" + id +
                ", competition=" + competition +
                ", participant=" + participant +
                ", mark=" + mark +
                ", answer='" + answer + '\'' +
                '}';
    }
}
