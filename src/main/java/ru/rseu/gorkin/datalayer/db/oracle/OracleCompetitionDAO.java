package ru.rseu.gorkin.datalayer.db.oracle;

import ru.rseu.gorkin.datalayer.dao.CompetitionDAO;
import ru.rseu.gorkin.datalayer.dto.Competition;
import ru.rseu.gorkin.datalayer.dto.CompetitionResultable;

import java.sql.Connection;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public class OracleCompetitionDAO implements CompetitionDAO {
    private Connection connection;

    public OracleCompetitionDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(String task, CompetitionResultable competitionResultable, Instant endRegistrationDate, Instant endSendingAnswerDate) {

    }

    @Override
    public List<Competition> getAll() {
        return null;
    }

    @Override
    public List<Competition> getActive() {
        return null;
    }

    @Override
    public List<Competition> getArchived() {
        return null;
    }

    @Override
    public List<Competition> getResolved(String userLogin) {
        return null;
    }

    @Override
    public List<Competition> getParticipateIn(String participantLogin) {
        return null;
    }

    @Override
    public Map<Competition, Boolean> getActiveWithParticipationIndicator(String participantLogin) {
        return null;
    }

    @Override
    public Map<Competition, Boolean> getAllWithParticipationIndicator(String participantLogin) {
        return null;
    }

    @Override
    public Map<Competition, Boolean> getArchivedWithParticipationIndicator(String participantLogin) {
        return null;
    }
}
