package ru.rseu.gorkin.datalayer.db.oracle;

import ru.rseu.gorkin.datalayer.dao.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;


public class OracleDBDAOFactory extends DAOFactory {
    private static volatile OracleDBDAOFactory instance;
    private Connection connection;

    private OracleDBDAOFactory() {
    }

    public static OracleDBDAOFactory getInstance()
            throws ClassNotFoundException, SQLException {
        OracleDBDAOFactory factory = instance;
        if (instance == null) {
            synchronized (OracleDBDAOFactory.class) {
                instance = factory = new OracleDBDAOFactory();
                factory.connected();
            }
        }
        return factory;
    }

    private void connected() throws ClassNotFoundException, SQLException {
        Locale.setDefault(Locale.ENGLISH);
        Class.forName("oracle.jdbc.driver.OracleDriver");
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String user = "system";
        String password = "123";
        connection = DriverManager.getConnection(url, user, password);
        initEnumIds(connection);
        System.out.println("Connected to oracle DB!");
    }

    private void initEnumIds(Connection connection) {
        EnumsInitializer enumsInitializer = new EnumsInitializer(connection);
        enumsInitializer.init();
    }

    @Override
    public UserDAO getUserDAO() {
        return new OracleUserDAO(connection);
    }

    @Override
    public DecisionDAO getDecisionDAO() {
        return new OracleDecisionDAO(connection);
    }

    @Override
    public CompetitionDAO getCompetitionDAO() {
        return new OracleCompetitionDAO(connection);
    }

    @Override
    public CompetitionParticipationDAO getCompetitionParticipationDAO() {
        return new OracleCompetitionParticipationDAO(connection);
    }

}
