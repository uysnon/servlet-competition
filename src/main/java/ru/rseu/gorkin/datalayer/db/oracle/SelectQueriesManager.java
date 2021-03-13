package ru.rseu.gorkin.datalayer.db.oracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class SelectQueriesManager<T> {
    public Collection<T> select(Connection connection,
                                String selectQuery,
                                Function<ResultSet, T> mapFunction) {
        List<T> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                result.add(mapFunction.apply(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public Collection<T> selectPrepare(
            PreparedStatement statement,
            Function<ResultSet, T> mapFunction) {
        List<T> result = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(mapFunction.apply(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}
