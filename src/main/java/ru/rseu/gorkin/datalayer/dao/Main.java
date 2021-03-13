package ru.rseu.gorkin.datalayer.dao;

import ru.rseu.gorkin.datalayer.db.oracle.OracleDBDAOFactory;
import ru.rseu.gorkin.datalayer.dto.User;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DAOFactory daoFactory = OracleDBDAOFactory.getInstance();
        User user_1 = daoFactory.getUserDAO().get(2);
        User user_2 = daoFactory.getUserDAO().get("test_admin");
        System.out.println(user_1);
        daoFactory.getUserDAO().unblock("test_blocked_admin");
        User user_1_1 = daoFactory.getUserDAO().get("test_blocked_admin");
        System.out.println(user_1_1);
        daoFactory.getUserDAO().delete("test_blocked_admin");
        User user_1_2 = daoFactory.getUserDAO().get("test_blocked_admin");
        System.out.println(user_1_2);
        daoFactory.getUserDAO().block("test_blocked_admin");
        User user_1_3 = daoFactory.getUserDAO().get("test_blocked_admin");
        System.out.println(user_1_3);
        System.out.println(user_2);
        System.out.println(daoFactory.getUserDAO().authenticate("test_admin1","test_admin1"));
    }
}
