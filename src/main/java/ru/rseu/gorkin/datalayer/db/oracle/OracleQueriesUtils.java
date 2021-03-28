package ru.rseu.gorkin.datalayer.db.oracle;

import ru.rseu.gorkin.datalayer.dto.*;
import ru.rseu.gorkin.resources.utils.ConfigurationManager;
import ru.rseu.gorkin.resources.utils.ConfigurationManagers;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
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
        User user = (User) selectQueriesManager.selectPrepare(preparedStatement, userMapFunction).stream().findAny().orElseThrow(() -> new IllegalArgumentException());
        preparedStatement.close();
        return user;
    }

    public User getUser(Connection connection, String login) throws Throwable {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.user.select.byLogin");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, login);
        Function<ResultSet, User> userMapFunction = getUserMapFunction();
        User user = (User) selectQueriesManager.selectPrepare(preparedStatement, userMapFunction).stream().findAny().orElseThrow(() -> new IllegalArgumentException());
        preparedStatement.close();
        return user;
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
        Collection<User> users = selectQueriesManager.selectPrepare(preparedStatement, userMapFunction);
        preparedStatement.close();
        return users;
    }

    public Collection<User> getAllUsersByStatus(Connection connection, Statuses status) throws Throwable {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.user.select.allByStatus");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, status.getId());
        Function<ResultSet, User> userMapFunction = getUserMapFunction();
        Collection<User> users = selectQueriesManager.selectPrepare(preparedStatement, userMapFunction);
        preparedStatement.close();
        return users;
    }

    public void updateUserStatus(Connection connection, String login, Statuses newStatus) throws SQLException {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.user.update.statusByLogin");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, newStatus.getId());
        preparedStatement.setString(2, login);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public int calculateCountUsersWithLogin(Connection connection, String login) throws SQLException {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.user.countWithLogin");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int countUsersWithLogin = resultSet.getInt(1);
        resultSet.close();
        preparedStatement.close();
        return countUsersWithLogin;
    }

    public void createUser(Connection connection, String login, String password, String name, Roles role) throws SQLException {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.user.create");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, name);
        preparedStatement.setInt(4, role.getId());
        preparedStatement.execute();
        preparedStatement.close();
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
        Competition competition = (Competition) selectQueriesManager.selectPrepare(preparedStatement, getCompetitionMapFunction()).stream().findAny().orElseThrow(() -> new IllegalArgumentException());
        Collection<User> experts = getExperts(connection, id);
        competition.setExperts(new ArrayList<>(experts));
        preparedStatement.close();
        return competition;
    }

    public Collection<User> getExperts(Connection connection, int competitionId) throws SQLException {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.competition.selectExpertsByCompetitionId");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, competitionId);
        Collection<User> users = selectQueriesManager.selectPrepare(preparedStatement, getUserMapFunction());
        preparedStatement.close();
        return users;
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
        String queryFindAddedCompetitionId = ConfigurationManagers.SQL_MANAGER.getProperty("qeury.competition.selectMaxId");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(queryFindAddedCompetitionId);
        if (rs.next()) {
            generatedId = rs.getInt(1);
        }
        rs.close();
        preparedStatement.close();
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
        preparedStatement.close();
    }

    public void participate(Connection connection, int competitionId, int participantId) throws SQLException {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.competitionParticipation.add");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, participantId);
        preparedStatement.setInt(2, competitionId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public Collection<CompetitionParticipation> getCompetitionParticipationByCompetitionId(Connection connection, int competitionId) throws SQLException {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.competitionParticipation.select.byCompetitionId");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, competitionId);
        Collection<CompetitionParticipation> participations = selectQueriesManager.selectPrepare(preparedStatement, getCompetitionParticipationMapFunction(connection));
        preparedStatement.close();
        return participations;
    }

    public CompetitionParticipation getCompetitionParticipationById(Connection connection, int id) throws Throwable {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.competitionParticipation.select.byId");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        CompetitionParticipation participation = (CompetitionParticipation) selectQueriesManager.selectPrepare(preparedStatement, getCompetitionParticipationMapFunction(connection)).stream().findAny().orElseThrow(() -> new IllegalArgumentException());
        preparedStatement.close();
        return participation;
    }

    public Collection<CompetitionParticipation> getCompetitionParticipationsByParticipantId(Connection connection, int participantId) throws SQLException {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.competitionParticipation.select.byParticipantId");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, participantId);
        Collection<CompetitionParticipation> participations = selectQueriesManager.selectPrepare(preparedStatement, getCompetitionParticipationMapFunction(connection));
        preparedStatement.close();
        return participations;
    }

    public Collection<Decision> getDecisionsByCompetitionId(Connection connection, int competitionId) throws SQLException {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.decision.byCompetitionId");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, competitionId);
        Collection<Decision> decisions = selectQueriesManager.selectPrepare(preparedStatement, getDecisionMapFunction(connection));
        preparedStatement.close();
        return decisions;
    }

    public int calculateCompetitionParticipationsCount(Connection connection, int competitionId, int userId) throws SQLException {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.competitionParticipation.select.countByUserAndCompetition");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, competitionId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt("COUNT");
        resultSet.close();
        preparedStatement.close();
        return count;
    }

    public Collection<CompetitionParticipation> getCompetitionParticipationsNeedToVerify(Connection connection, int expertId) throws SQLException {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.competitionParticipation.select.needToVerifyByExpertId");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, expertId);
        preparedStatement.setInt(2, expertId);
        Collection<CompetitionParticipation> participations = selectQueriesManager.selectPrepare(preparedStatement, getCompetitionParticipationMapFunction(connection));
        preparedStatement.close();
        return participations;
    }

    public void sendAnswer(Connection connection, int competitionParticipationId, String answer) throws SQLException {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.competitionParticipation.insert.answer");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, answer);
        preparedStatement.setInt(2, competitionParticipationId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void makeDecision(Connection connection, int expertId, int competitionParticipationId, Marks mark, String comment) throws SQLException {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.decision.insert");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, expertId);
        preparedStatement.setInt(2, competitionParticipationId);
        preparedStatement.setInt(3, mark.getId());
        preparedStatement.setString(4, comment);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public Collection<Decision> getDecisionsByCompetitionParticipationId(Connection connection, int competitionParticipationId) throws SQLException {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.decision.select.byCompetitionIdAndParticipantId");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, competitionParticipationId);
        Collection<Decision> decisions = selectQueriesManager.selectPrepare(preparedStatement, getDecisionMapFunction(connection));
        preparedStatement.close();
        return decisions;
    }

    public CompetitionParticipation getCompetitionParticipationByUserAndCompetitionId(Connection connection, int userId, int competitionId) throws SQLException {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.competitionParticipation.select.byUserAndCompetition");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, competitionId);
        CompetitionParticipation participation = (CompetitionParticipation) selectQueriesManager.selectPrepare(preparedStatement, getCompetitionParticipationMapFunction(connection)).stream().findAny().orElse(null);
        preparedStatement.close();
        return participation;
    }

    public Collection<Decision> getDecisionsByExpertId(Connection connection, int expertId) throws SQLException {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.decision.byExpertId");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, expertId);
        Collection<Decision> decisions = selectQueriesManager.selectPrepare(preparedStatement, getDecisionMapFunction(connection));
        preparedStatement.close();
        return decisions;
    }

    public Decision getDecisionById(Connection connection, int id) throws SQLException {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.decision.select.byId");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        Collection<Decision> decisions = selectQueriesManager.selectPrepare(preparedStatement, getDecisionMapFunction(connection));
        return decisions.stream().findAny().orElse(null);
    }

    public int getCountDecisionByExpertAndParticipationId(Connection connection, int expertId, int participationId) throws SQLException {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.decision.select.countByExpertAndParticipationId");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, expertId);
        preparedStatement.setInt(2, participationId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt("COUNT");
        resultSet.close();
        preparedStatement.close();
        return count;
    }

    public void setMarkToCompetitionParticipation(Connection connection, int compeitionParticipationId, int markId) throws SQLException {
        String query = ConfigurationManagers.SQL_MANAGER.getProperty("query.competitionParticipation.updateMark");
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, markId);
        preparedStatement.setInt(2, compeitionParticipationId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
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

    private Function<ResultSet, Decision> getDecisionMapFunction(Connection connection) {
        return (resultSet) -> {
            int id = 0;
            Marks mark = null;
            User expert = null;
            CompetitionParticipation competitionParticipation = null;
            String comment = "";
            try {
                id = resultSet.getInt("ID");
                mark = Marks.getInstance(resultSet.getInt("MARK_ID"));
                int userId = resultSet.getInt("USER_ID");
                int competitionParticipationId = resultSet.getInt("COMPETITION_PARTICIPATION_ID");
                comment = resultSet.getString("COMMENT_");
                expert = getUser(connection, userId);
                competitionParticipation = getCompetitionParticipationById(connection, competitionParticipationId);
            } catch (SQLException throwables) {
                throw new IllegalArgumentException();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            Decision decision = new Decision();
            decision.setId(id);
            decision.setMark(mark);
            decision.setExpert(expert);
            decision.setCompetitionParticipation(competitionParticipation);
            decision.setComment(comment);
            return decision;
        };
    }

    private Function<ResultSet, CompetitionParticipation> getCompetitionParticipationMapFunction(Connection connection) {
        return (resultSet) -> {
            int id = 0;
            Competition competition = null;
            User participant = null;
            Marks mark = null;
            String answer = "";
            try {
                id = resultSet.getInt("ID");
                mark = Marks.getInstance(resultSet.getInt("MARK_ID"));
                int userId = resultSet.getInt("USER_ID");
                int competitionId = resultSet.getInt("COMPETITION_ID");
                answer = resultSet.getString("ANSWER");
                participant = getUser(connection, userId);
                competition = getCompetitionById(connection, competitionId);
            } catch (SQLException throwables) {
                throw new IllegalArgumentException();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            CompetitionParticipation participation = new CompetitionParticipation();
            participation.setId(id);
            participation.setAnswer(answer);
            participation.setMark(mark);
            participation.setCompetition(competition);
            participation.setParticipant(participant);
            return participation;
        };
    }
}
