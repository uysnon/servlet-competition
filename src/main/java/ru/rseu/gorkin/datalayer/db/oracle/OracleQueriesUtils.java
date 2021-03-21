package ru.rseu.gorkin.datalayer.db.oracle;

import ru.rseu.gorkin.datalayer.dto.*;
import ru.rseu.gorkin.resources.utils.ConfigurationManager;
import ru.rseu.gorkin.resources.utils.ConfigurationManagers;

import java.sql.*;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class OracleQueriesUtils {
    private SelectQueriesManager selectQueriesManager;

    public OracleQueriesUtils() {
        selectQueriesManager = new SelectQueriesManager();
    }

    public User getUser(Connection connection, int id) throws Throwable {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.user.select.byId");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        Function<ResultSet, User> userMapFunction = getUserMapFunction();
        return (User) selectQueriesManager.selectPrepare(preparedStatement, userMapFunction).stream().findAny().orElseThrow(() -> new IllegalArgumentException());
    }

    public User getUser(Connection connection, String login) throws Throwable {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.user.select.byLogin");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, login);
        Function<ResultSet, User> userMapFunction = getUserMapFunction();
        return (User) selectQueriesManager.selectPrepare(preparedStatement, userMapFunction).stream().findAny().orElseThrow(() -> new IllegalArgumentException());
    }

    public Collection<User> getAllUsers(Connection connection) {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.user.select.all");
        Function<ResultSet, User> userMapFunction = getUserMapFunction();
        return selectQueriesManager.select(connection, query, userMapFunction);
    }

    public Collection<User> getAllUsersByRole(Connection connection, Roles role) throws Throwable {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.user.select.allByRole");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, role.getId());
        Function<ResultSet, User> userMapFunction = getUserMapFunction();
        return selectQueriesManager.selectPrepare(preparedStatement, userMapFunction);
    }

    public Collection<User> getAllUsersByStatus(Connection connection, Statuses status) throws Throwable {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.user.select.allByStatus");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, status.getId());
        Function<ResultSet, User> userMapFunction = getUserMapFunction();
        return selectQueriesManager.selectPrepare(preparedStatement, userMapFunction);
    }

    public void updateUserStatus(Connection connection, String login, Statuses newStatus) throws SQLException {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.user.update.statusByLogin");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, newStatus.getId());
        preparedStatement.setString(2, login);
        preparedStatement.executeUpdate();
    }

    public int calculateCountUsersWithLogin(Connection connection, String login) throws SQLException {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.user.countWithLogin");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }

    public void createUser(Connection connection, String login, String password, String name, Roles role) throws SQLException {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.user.create");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, name);
        preparedStatement.setInt(4, role.getId());
        preparedStatement.execute();
    }

    public Collection<Competition> getAllCompetitions(Connection connection) {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.competition.select.all");
        Collection<Competition> competitions = selectQueriesManager.select(connection, query, getCompetitionMapFunction());
        return competitions;
    }

    public Competition getCompetitionById(Connection connection, int id) throws Throwable {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.competition.select.byId");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        return (Competition) selectQueriesManager.selectPrepare(preparedStatement, getCompetitionMapFunction()).stream().findAny().orElseThrow(() -> new IllegalArgumentException());
    }

    public Collection<User> getExperts(Connection connection, int competitionId) throws SQLException {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.competition.selectExpertsByCompetitionId");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, competitionId);
        return selectQueriesManager.selectPrepare(preparedStatement, getUserMapFunction());
    }

    public int addCompetition(Connection connection, String task, StrategyAdapters.StrategySQL strategySQL, Instant endRegistrationDate, Instant endSendingAnswersDate) throws SQLException {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.competition.add");

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, task);
        preparedStatement.setInt(2, strategySQL.getId());
        preparedStatement.setDouble(3, strategySQL.getValue());
        preparedStatement.setTimestamp(4, Timestamp.from(endRegistrationDate));
        preparedStatement.setTimestamp(5, Timestamp.from(endSendingAnswersDate));
        preparedStatement.executeUpdate();

        int generatedId = -1;
        String queryFindAddedCompetitionId = ConfigurationManagers.SQL_MANAGER.getProperty("qeury.competition.maxId");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(queryFindAddedCompetitionId);
        if (rs.next()) {
            generatedId = rs.getInt(1);
        }
        return generatedId;
    }

    public void addCompetitionEvaluation(Connection connection, int competitionId, List<Integer> expertIds) throws SQLException {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.competitionEvaluation.add");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int expertId : expertIds) {
            preparedStatement.setInt(1, competitionId);
            preparedStatement.setInt(2, expertId);
            preparedStatement.addBatch();

        }
        preparedStatement.executeBatch();
    }


    private Function<ResultSet, User> getUserMapFunction() {
        return (resultSet) -> {
            Roles role = null;
            Statuses status = null;
            String login = null;
            String password = null;
            String name = null;
            int id = 0;
            try {
                id = resultSet.getInt("ID");
                role = Roles.getInstance(resultSet.getInt("ROLE_ID"));
                status = Statuses.getInstance(resultSet.getInt("STATUS_ID"));
                login = resultSet.getString("LOGIN");
                password = resultSet.getString("PASSWORD");
                name = resultSet.getString("NAME");
            } catch (SQLException throwables) {
                throw new IllegalArgumentException();
            }
            return new User(id, login, password, name, role, status);
        };
    }

    private Function<ResultSet, Competition> getCompetitionMapFunction() {
        return (resultSet) -> {
            int id = 0;
            CompetitionResultable evaluationStrategy;
            int strategyId = 0;
            double strategyValue = 0;
            String task;
            Instant endRegistrationDate;
            Instant endSendingAnswerDate;
            try {
                id = resultSet.getInt("ID");
                task = resultSet.getString("TASK");
                strategyId = resultSet.getInt("STRATEGY_ID");
                strategyValue = resultSet.getDouble("STRATEGY_VALUE");
                evaluationStrategy = StrategyAdapters.generateStrategyDTO(strategyId, strategyValue);
                endRegistrationDate = resultSet.getTimestamp("END_REGISTRATION_DATE").toInstant();
                endSendingAnswerDate = resultSet.getTimestamp("END_SENDING_ANSWER_DATE").toInstant();
            } catch (SQLException throwables) {
                throw new IllegalArgumentException();
            }
            Competition competition = new Competition();
            competition.setId(id);
            competition.setTask(task);
            competition.setEvaluationStrategy(evaluationStrategy);
            competition.setEndRegistrationDate(endRegistrationDate);
            competition.setEndSendingAnswerDate(endSendingAnswerDate);
            return competition;
        };
    }


}
