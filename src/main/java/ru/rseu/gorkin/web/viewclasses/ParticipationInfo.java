package ru.rseu.gorkin.web.viewclasses;

import ru.rseu.gorkin.datalayer.dto.Marks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParticipationInfo {
    private int id;
    private String participantName;
    private List<Marks> marks;
    private Marks totalMark;

    ParticipationInfo() {
        marks = new ArrayList<>();
    }

    ParticipationInfo(String participantName, List<Marks> marks, Marks totalMark) {
        this.participantName = participantName;
        this.marks = marks;
        this.totalMark = totalMark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public List<Marks> getMarks() {
        return marks;
    }

    public void setMarks(List<Marks> marks) {
        this.marks = marks;
    }

    public Marks getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(Marks totalMark) {
        this.totalMark = totalMark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParticipationInfo)) return false;
        ParticipationInfo that = (ParticipationInfo) o;
        return Objects.equals(participantName, that.participantName) &&
                Objects.equals(marks, that.marks) &&
                Objects.equals(totalMark, that.totalMark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participantName, marks, totalMark);
    }

    @Override
    public String toString() {
        return "ParticipationInfo{" +
                "participantName='" + participantName + '\'' +
                ", marks=" + marks +
                ", totalMark=" + totalMark +
                '}';
    }
}
