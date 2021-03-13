package ru.rseu.gorkin.datalayer.dao;

import ru.rseu.gorkin.datalayer.dto.CompetitionParticipation;

import java.util.List;

public interface CompetitionParticipationDAO {
    public void participate(int participationId, String participantLogin);
    List<CompetitionParticipation> getVerificationRequiredParticipations(String expertLogin);
    List<CompetitionParticipation> getVerificatedParticipations(String expertLogin);
    void makeAnswer(int participationId, String answer);
}
