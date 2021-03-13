package ru.rseu.gorkin.datalayer.dto;

import java.util.Objects;

public class Decision {
    private int id;
    private Marks mark;
    private User expert;
    private CompetitionParticipation competitionParticipation;
    private String comment;

    public Decision() {
    }

    public Decision(int id, Marks mark, User expert, CompetitionParticipation competitionParticipation, String comment) {
        this.id = id;
        this.mark = mark;
        this.expert = expert;
        this.competitionParticipation = competitionParticipation;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Marks getMark() {
        return mark;
    }

    public void setMark(Marks mark) {
        this.mark = mark;
    }

    public User getExpert() {
        return expert;
    }

    public void setExpert(User expert) {
        this.expert = expert;
    }

    public CompetitionParticipation getCompetitionParticipation() {
        return competitionParticipation;
    }

    public void setCompetitionParticipation(CompetitionParticipation competitionParticipation) {
        this.competitionParticipation = competitionParticipation;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Decision)) return false;
        Decision decision = (Decision) o;
        return id == decision.id &&
                mark == decision.mark &&
                Objects.equals(expert, decision.expert) &&
                Objects.equals(competitionParticipation, decision.competitionParticipation) &&
                Objects.equals(comment, decision.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mark, expert, competitionParticipation, comment);
    }

    @Override
    public String toString() {
        return "Decision{" +
                "id=" + id +
                ", mark=" + mark +
                ", expert=" + expert +
                ", competitionParticipation=" + competitionParticipation +
                ", comment='" + comment + '\'' +
                '}';
    }
}
