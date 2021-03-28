package ru.rseu.gorkin.datalayer.db.oracle;

import ru.rseu.gorkin.datalayer.dao.DecisionDAO;
import ru.rseu.gorkin.datalayer.dto.Decision;
import ru.rseu.gorkin.datalayer.dto.Marks;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OracleDecisionDAO implements DecisionDAO {
    private Connection connection;
    private OracleQueriesUtils oracleQueriesUtils;

    public OracleDecisionDAO(Connection connection) {
        this.connection = connection;
        this.oracleQueriesUtils = new OracleQueriesUtils();
    }

    @Override
    public void makeDecision(int expertId, int competitionParticipationId, Marks mark, String comment) {
        try {
            oracleQueriesUtils.makeDecision(connection, expertId, competitionParticipationId, mark, comment);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Decision> getAll() {
        return null;
    }

    @Override
    public Decision get(int id) {
        Decision decision = null;
        try {
            decision = oracleQueriesUtils.getDecisionById(connection, id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return decision;
    }

    @Override
    public List<Decision> getExpertDecisions(int expertId) {
        List<Decision> decisions = null;
        try {
            decisions = new ArrayList<>(oracleQueriesUtils.getDecisionsByExpertId(connection, expertId));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return decisions;
    }

    @Override
    public List<Decision> getDecisionsByCompetitionId(int competitionId) {
        try {
            return new ArrayList(oracleQueriesUtils.getDecisionsByCompetitionId(connection, competitionId));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Decision> getDecisionsByCompetitionParticipationId(int competitionParticipationId) {
        List result = null;
        try {
            result = new ArrayList<>(oracleQueriesUtils.getDecisionsByCompetitionParticipationId(connection, competitionParticipationId));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean isDecisionMade(int expertId, int participationId) {
        boolean result = false;
        try {
            int count = oracleQueriesUtils.getCountDecisionByExpertAndParticipationId(connection, expertId, participationId);
            if (count > 0) {
                result = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }
}
