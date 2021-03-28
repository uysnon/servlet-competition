package ru.rseu.gorkin.datalayer.dao;

import ru.rseu.gorkin.datalayer.dto.Decision;
import ru.rseu.gorkin.datalayer.dto.Marks;

import java.util.List;

public interface DecisionDAO {
    void makeDecision(int expertId, int competitionParticipationId, Marks mark, String comment);
    List<Decision> getAll();
    List<Decision> getExpertDecisions(int expertId);
    List<Decision> getDecisionsByCompetitionId(int competitionId);
    List<Decision> getDecisionsByCompetitionParticipationId(int competitionParticipationId);
}
