package ru.rseu.gorkin.datalayer.db.oracle;

import ru.rseu.gorkin.datalayer.dto.Marks;
import ru.rseu.gorkin.datalayer.dto.Roles;
import ru.rseu.gorkin.datalayer.dto.Statuses;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.function.Function;

public class EnumsInitializer {
    Connection connection;
    SelectQueriesManager<PrimitiveDatabaseComponent> selectQueriesManager;
    Function<ResultSet, PrimitiveDatabaseComponent> mapFunction;

    public EnumsInitializer(Connection connection) {
        this.selectQueriesManager = new SelectQueriesManager<>();
        this.connection = connection;

    }

    public void init() {
        initMapFunction();
        initMarks();
        initRoles();
        initStatuses();
    }

    private void initMapFunction() {
        this.mapFunction = (resultSet) -> {
            int id = 0;
            String title = "";
            String description = "";
            try {
                id = resultSet.getInt("ID");
                title = resultSet.getString("TITLE");
                description = resultSet.getString("DESCRIPTION");
            } catch (SQLException throwables) {
                System.out.println(throwables);
            }
            return new PrimitiveDatabaseComponent(id, title, description);
        };
    }

    private void initRoles() {
        String query = "SELECT ID, TITLE, DESCRIPTION FROM ROLE";
        Collection<PrimitiveDatabaseComponent> sqlRoles = selectQueriesManager.select(
                connection,
                query,
                mapFunction);
        for (Roles role : Roles.values()) {
            int id = role.getId();
            for (PrimitiveDatabaseComponent sqlRole : sqlRoles) {
                if (id == sqlRole.getId()) {
                    role.setTitle(sqlRole.getTitle());
                    role.setDescription(sqlRole.getDescription());
                }
            }
        }
    }

    private void initMarks() {
        String query = "SELECT ID, TITLE FROM MARK";
        Collection<PrimitiveDatabaseComponent> sqlRoles = selectQueriesManager.select(
                connection,
                query,
                mapFunction);
        for (Marks mark : Marks.values()) {
            int id = mark.getId();
            for (PrimitiveDatabaseComponent sqlMark : sqlRoles) {
                if (id == sqlMark.getId()) {
                    mark.setTitle(sqlMark.getTitle());
                }
            }
        }
    }


    private void initStatuses() {
        String query = "SELECT ID, TITLE, DESCRIPTION FROM STATUS_";
        Collection<PrimitiveDatabaseComponent> sqlRoles = selectQueriesManager.select(
                connection,
                query,
                mapFunction);
        for (Statuses status : Statuses.values()) {
            int id = status.getId();
            for (PrimitiveDatabaseComponent sqlStatus : sqlRoles) {
                if (id == sqlStatus.getId()) {
                    status.setTitle(sqlStatus.getTitle());
                    status.setDescription(sqlStatus.getDescription());
                }
            }
        }
    }

    public static class PrimitiveDatabaseComponent {
        private String title;
        private int id;
        private String description;

        public PrimitiveDatabaseComponent(int id, String title, String description) {
            this.title = title;
            this.id = id;
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
