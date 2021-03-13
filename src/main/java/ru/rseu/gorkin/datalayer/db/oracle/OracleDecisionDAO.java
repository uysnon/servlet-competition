package ru.rseu.gorkin.datalayer.db.oracle;

import ru.rseu.gorkin.datalayer.dao.DecisionDAO;
import ru.rseu.gorkin.datalayer.dto.Decision;

import java.sql.Connection;
import java.util.List;

public class OracleDecisionDAO implements DecisionDAO {
    private Connection connection;

    public OracleDecisionDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void makeDecision(Decision decision) {

    }

    @Override
    public List<Decision> getAll() {
        return null;
    }

    @Override
    public List<Decision> getExpertDecisions(String expertLogin) {
        return null;
    }
}
