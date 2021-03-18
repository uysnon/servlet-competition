package ru.rseu.gorkin.datalayer.db.oracle;

import ru.rseu.gorkin.datalayer.dao.AuthenticationResults;
import ru.rseu.gorkin.datalayer.dao.UserDAO;
import ru.rseu.gorkin.datalayer.dto.Roles;
import ru.rseu.gorkin.datalayer.dto.Statuses;
import ru.rseu.gorkin.datalayer.dto.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OracleUserDAO implements UserDAO {
    private Connection connection;
    private OracleQueriesUtils oracleQueriesUtils;

    public OracleUserDAO(Connection connection) {
        this.connection = connection;
        this.oracleQueriesUtils = new OracleQueriesUtils();
    }


    @Override
    public void block(String login) {
        try {
            oracleQueriesUtils.updateUserStatus(connection, login, Statuses.BLOCKED);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void unblock(String login) {
        try {
            oracleQueriesUtils.updateUserStatus(connection, login, Statuses.ACTIVE);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public void delete(String login) {
        try {
            oracleQueriesUtils.updateUserStatus(connection, login, Statuses.DELETED);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public List<User> getAll() {
        return new ArrayList<>(oracleQueriesUtils.getAllUsers(connection));
    }

    @Override
    public List<User> getAllByRole(Roles role) {
        try {
            return new ArrayList<>(oracleQueriesUtils.getAllUsersByRole(connection, role));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAllByStatus(Statuses status) {
        try {
            return new ArrayList<>(oracleQueriesUtils.getAllUsersByStatus(connection, status));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public User get(int id) {
        try {
            return oracleQueriesUtils.getUser(connection, id);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public User get(String login) {
        try {
            return oracleQueriesUtils.getUser(connection, login);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public AuthenticationResults authenticate(String login, String password) {
        User user = null;
        try {
            user = oracleQueriesUtils.getUser(connection, login);
        } catch (Throwable throwable) {
            return AuthenticationResults.NO_SUCH_USER_FOUND;
        }
        if (user.getStatus() == Statuses.BLOCKED) {
            return AuthenticationResults.USER_BLOCKED;
        }
        if (user.getStatus() == Statuses.DELETED) {
            return AuthenticationResults.USER_DELETED;
        }
        if (user.getPassword().equals(password)) {
            return AuthenticationResults.SUCCESS;
        } else {
            return AuthenticationResults.WRONG_PASSWORD;
        }
    }

    @Override
    public boolean isLoginExist(String login) {
        boolean result = false;
        try {
            result = (oracleQueriesUtils.calculateCountUsersWithLogin(connection, login) >= 1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public void createUser(String login, String password, String name, Roles role) {
        try {
            oracleQueriesUtils.createUser(connection, login, password, name, role);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
