package org.dev.nebula.core.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.dev.nebula.core.db.DaoBase;
import org.dev.nebula.core.db.DatabaseManager;
import org.dev.nebula.core.db.models.UserData;

public class UserDao extends DaoBase {
    public UserDao(DatabaseManager databaseManager) {
        super(databaseManager);
    }

    public UserData loadUser(UUID id) throws SQLException {
        try (Connection c = db.getConnection()) {
            PreparedStatement st = c.prepareStatement(
                "SELECT * FROM users WHERE id = ?"
            );
            st.setObject(1, id);

            ResultSet rs = st.executeQuery();
            if (!rs.next()) return null;

            return new UserData(
                id,
                rs.getString("nickName"),
                rs.getInt("level"),
                rs.getInt("experience")
            );
        }
    }
}
