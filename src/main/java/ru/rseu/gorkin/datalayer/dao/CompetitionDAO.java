package ru.rseu.gorkin.datalayer.dao;

import ru.rseu.gorkin.datalayer.dto.Competition;
import ru.rseu.gorkin.datalayer.dto.CompetitionResultable;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface CompetitionDAO {
    void add(String task, CompetitionResultable competitionResultable, Instant endRegistrationDate, Instant endSendingAnswerDate);

    List<Competition> getAll();
    List<Competition> getActive();
    List<Competition> getArchived();

    List<Competition> getResolved(String userLogin);
    List<Competition> getParticipateIn(String participantLogin);
    Map<Competition, Boolean> getActiveWithParticipationIndicator(String participantLogin);
    Map<Competition, Boolean> getAllWithParticipationIndicator(String participantLogin);
    Map<Competition, Boolean> getArchivedWithParticipationIndicator(String participantLogin);



}
