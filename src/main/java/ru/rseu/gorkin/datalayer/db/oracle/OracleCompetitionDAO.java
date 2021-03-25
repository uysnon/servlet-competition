package ru.rseu.gorkin.datalayer.db.oracle;

import ru.rseu.gorkin.datalayer.dao.CompetitionDAO;
import ru.rseu.gorkin.datalayer.dto.Competition;
import ru.rseu.gorkin.datalayer.dto.CompetitionResultable;
import ru.rseu.gorkin.datalayer.dto.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class OracleCompetitionDAO implements CompetitionDAO {
    private Connection connection;
    private OracleQueriesUtils oracleQueriesUtils;

    public OracleCompetitionDAO(Connection connection) {
        this.connection = connection;
        this.oracleQueriesUtils = new OracleQueriesUtils();
    }

    @Override
    public void add(String task, CompetitionResultable competitionResultable, Instant endRegistrationDate, Instant endSendingAnswerDate, List<Integer> expertIds) {
        try {
            int competitionId = oracleQueriesUtils.addCompetition(connection, task, StrategyAdapters.generateStrategySQL(competitionResultable), endRegistrationDate, endSendingAnswerDate);
            oracleQueriesUtils.addCompetitionEvaluation(connection, competitionId, expertIds);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public List<Competition> getAll() {
        return new ArrayList<>(oracleQueriesUtils.getAllCompetitions(connection));
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
    public Competition getById(int id) {
        try {
            Competition competition = oracleQueriesUtils.getCompetitionById(connection, id);
            return competition;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
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
