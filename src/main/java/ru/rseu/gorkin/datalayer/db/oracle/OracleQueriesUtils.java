package ru.rseu.gorkin.datalayer.db.oracle;

import ru.rseu.gorkin.datalayer.dto.Roles;
import ru.rseu.gorkin.datalayer.dto.Statuses;
import ru.rseu.gorkin.datalayer.dto.User;
import ru.rseu.gorkin.resources.utils.ConfigurationManagers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
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
        String query = "SELECT COUNT(*) FROM USER_ WHERE LOGIN = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }

    public void createUser(Connection connection, String login, String password, String name, Roles role) throws SQLException {
        String query = "INSERT INTO USER_ (LOGIN, PASSWORD, NAME, ROLE_ID, STATUS_ID) VALUES (?, ?, ?, ?, 1)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, name);
        preparedStatement.setInt(4, role.getId());
        preparedStatement.execute();
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


}
