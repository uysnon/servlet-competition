package ru.rseu.gorkin.datalayer.db.oracle;

import ru.rseu.gorkin.datalayer.dao.CompetitionParticipationDAO;
import ru.rseu.gorkin.datalayer.dto.CompetitionParticipation;
import ru.rseu.gorkin.datalayer.dto.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OracleCompetitionParticipationDAO implements CompetitionParticipationDAO {
    private Connection connection;
    private OracleQueriesUtils oracleQueriesUtils;

    public OracleCompetitionParticipationDAO(Connection connection) {
        this.connection = connection;
        this.oracleQueriesUtils = new OracleQueriesUtils();
    }


    @Override
    public void participate(int competitionId, int participantId) {
        try {
            oracleQueriesUtils.participate(connection, competitionId, participantId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean isParticipate(int competitionId, int participantId) {
        boolean result = false;
        try {
            int count = oracleQueriesUtils.calculateCompetitionParticipationsCount(connection, competitionId, participantId);
            if (count > 0) {
                result = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public CompetitionParticipation get(int id) {
        return null;
    }

    @Override
    public CompetitionParticipation get(int competitionId, int participantId) {
        CompetitionParticipation competitionParticipation = null;
        try {
            competitionParticipation = oracleQueriesUtils.getCompetitionParticipationByUserAndCompetitionId(connection, participantId, competitionId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return competitionParticipation;
    }

    @Override
    public List<User> getWinners(int competitionId) {
        return null;
    }

    @Override
    public List<CompetitionParticipation> getVerificationRequiredParticipations(int expertId) {
        List<CompetitionParticipation> competitionParticipations = null;
        try {
            competitionParticipations = new ArrayList<>(oracleQueriesUtils.getCompetitionParticipationsNeedToVerify(connection, expertId));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return competitionParticipations;
    }

    @Override
    public void makeAnswer(int participationId, String answer) {
        try {
            oracleQueriesUtils.sendAnswer(connection,participationId, answer);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<CompetitionParticipation> getByCompetitionId(int competitionId) {
        try {
            return new ArrayList<>(oracleQueriesUtils.getCompetitionParticipationByCompetitionId(connection, competitionId));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
