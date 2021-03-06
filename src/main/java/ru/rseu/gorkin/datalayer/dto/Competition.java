package ru.rseu.gorkin.datalayer.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Competition {
    private int id;
    private CompetitionResultable evaluationStrategy;
    private String task;
    private Instant endRegistrationDate;
    private Instant endSendingAnswerDate;
    private List<User> experts;

    public Competition() {
        experts = new ArrayList<>();
    }


    public Competition(int id, CompetitionResultable evaluationStrategy, String task, Instant endRegistrationDate, Instant endSendingAnswerDate, List<User> experts) {
        this.id = id;
        this.evaluationStrategy = evaluationStrategy;
        this.task = task;
        this.endRegistrationDate = endRegistrationDate;
        this.endSendingAnswerDate = endSendingAnswerDate;
        this.experts = experts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CompetitionResultable getEvaluationStrategy() {
        return evaluationStrategy;
    }

    public void setEvaluationStrategy(CompetitionResultable evaluationStrategy) {
        this.evaluationStrategy = evaluationStrategy;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Instant getEndRegistrationDate() {
        return endRegistrationDate;
    }

    public void setEndRegistrationDate(Instant endRegistrationDate) {
        this.endRegistrationDate = endRegistrationDate;
    }

    public Instant getEndSendingAnswerDate() {
        return endSendingAnswerDate;
    }

    public void setEndSendingAnswerDate(Instant endSendingAnswerDate) {
        this.endSendingAnswerDate = endSendingAnswerDate;
    }

    public List<User> getExperts() {
        return experts;
    }

    public void setExperts(List<User> experts) {
        this.experts = experts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Competition)) return false;
        Competition that = (Competition) o;
        return id == that.id &&
                evaluationStrategy == that.evaluationStrategy &&
                Objects.equals(task, that.task) &&
                Objects.equals(endRegistrationDate, that.endRegistrationDate) &&
                Objects.equals(endSendingAnswerDate, that.endSendingAnswerDate) &&
                Objects.equals(experts, that.experts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, evaluationStrategy, task, endRegistrationDate, endSendingAnswerDate, experts);
    }

    @Override
    public String toString() {
        return "Competition{" +
                "id=" + id +
                ", evaluationStrategy=" + evaluationStrategy +
                ", task='" + task + '\'' +
                ", endRegistrationDate=" + endRegistrationDate +
                ", endSendingAnswerDate=" + endSendingAnswerDate +
                ", experts=" + experts +
                '}';
    }
}
