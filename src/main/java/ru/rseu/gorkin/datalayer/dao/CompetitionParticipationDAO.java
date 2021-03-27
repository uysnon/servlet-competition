package ru.rseu.gorkin.datalayer.dao;

import ru.rseu.gorkin.datalayer.dto.CompetitionParticipation;
import ru.rseu.gorkin.datalayer.dto.User;

import java.util.List;

public interface CompetitionParticipationDAO {
    void participate(int competitionId, int participantId);
    boolean isParticipate(int competitionId, int participantId);
    CompetitionParticipation get(int id);
    CompetitionParticipation get(int competitionId, int participantId);
    List<User> getWinners(int competitionId);
    List<CompetitionParticipation> getVerificationRequiredParticipations(int expertId);
    void makeAnswer(int participationId, String answer);
    List<CompetitionParticipation> getByCompetitionId(int competitionId);
    List<CompetitionParticipation> getByParticipantId(int participantId);
}
