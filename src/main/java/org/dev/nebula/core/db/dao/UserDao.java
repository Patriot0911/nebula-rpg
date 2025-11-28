package org.dev.nebula.core.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.dev.nebula.core.db.DaoBase;
import org.dev.nebula.core.db.DatabaseManager;
import org.dev.nebula.core.db.models.SkillData;
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

    public UserData createUser(UUID id, String nickname) throws SQLException {
        try (Connection c = db.getConnection()) {
            PreparedStatement st = c.prepareStatement(
                "INSERT INTO users (id, nickname) VALUES (?, ?)"
            );
            st.setObject(1, id);
            st.setString(2, nickname);
            st.executeUpdate();
        }

        return new UserData(id, nickname, 1, 0);
    }

    public void saveUserSkills(UserData userData) throws SQLException {
        try (Connection c = db.getConnection()) {
            for (SkillData skill : userData.getAllSkills().values()) {
                if (skill.isNew()) {
                    PreparedStatement insert = c.prepareStatement(
                        "INSERT INTO user_skills (user_id, skill_id, level, data) VALUES (?, ?, ?, ?)"
                    );
                    insert.setObject(1, userData.getId());
                    insert.setObject(2, skill.getSkillId());
                    insert.setInt(3, skill.getLevel());
                    insert.setObject(4, skill.getData());
                    insert.executeUpdate();
                    skill.setNew(false);
                    continue;
                }

                if (skill.isModified()) {
                    PreparedStatement update = c.prepareStatement(
                        "UPDATE user_skills SET level = ?, data = ? WHERE user_id = ? AND skill_id = ?"
                    );
                    update.setInt(1, skill.getLevel());
                    update.setObject(2, skill.getData());
                    update.setObject(3, userData.getId());
                    update.setObject(4, skill.getSkillId());
                    update.executeUpdate();
                    skill.setModified(false);
                }
            }
        }
    }

    public void saveUser(UserData userData) throws SQLException {
        try (Connection c = db.getConnection()) {

            PreparedStatement st = c.prepareStatement(
                "UPDATE users SET nickname = ?, level = ?, exp = ? WHERE id = ?"
            );

            st.setString(1, userData.getNickName());
            st.setInt(2, userData.getLevel());
            st.setInt(3, userData.getExp());
            st.setObject(4, userData.getId());

            st.executeUpdate();
        }
    }
}
