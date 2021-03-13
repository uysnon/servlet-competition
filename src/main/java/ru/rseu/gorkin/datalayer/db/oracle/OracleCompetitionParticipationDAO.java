package ru.rseu.gorkin.datalayer.db.oracle;

import ru.rseu.gorkin.datalayer.dao.CompetitionParticipationDAO;
import ru.rseu.gorkin.datalayer.dto.CompetitionParticipation;

import java.sql.Connection;
import java.util.List;

public class OracleCompetitionParticipationDAO implements CompetitionParticipationDAO {
    private Connection connection;

    public OracleCompetitionParticipationDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void participate(int participationId, String participantLogin) {
        
    }

    @Override
    public List<CompetitionParticipation> getVerificationRequiredParticipations(String expertLogin) {
        return null;
    }

    @Override
    public List<CompetitionParticipation> getVerificatedParticipations(String expertLogin) {
        return null;
    }

    @Override
    public void makeAnswer(int participationId, String answer) {

    }
}
