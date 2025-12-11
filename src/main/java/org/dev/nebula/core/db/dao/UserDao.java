package org.dev.nebula.core.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import org.dev.nebula.core.db.DaoBase;
import org.dev.nebula.core.db.DatabaseManager;
import org.dev.nebula.core.db.models.AchievementUserData;
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
            st.setString(1, id.toString());

            ResultSet rs = st.executeQuery();
            if (!rs.next()) return null;

            return new UserData(
                id,
                rs.getString("nickName"),
                rs.getInt("level"),
                rs.getInt("exp")
            );
        }
    }

    @SuppressWarnings("unchecked")
    public void loadUserSkills(UserData userData) throws SQLException {
        try (Connection c = db.getConnection()) {
            PreparedStatement st = c.prepareStatement(
                "SELECT us.skill_id AS id, s.name, s.id AS skill_id, us.level, us.data " +
                "FROM user_skills us " +
                "JOIN skills s ON s.id = us.skill_id " +
                "WHERE us.user_id = ?"
            );
            st.setString(1, userData.getId().toString());

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                UUID skillId = UUID.fromString(rs.getString("skill_id"));
                String skillName = rs.getString("name");
                int level = rs.getInt("level");
                Map<String, Object> dataJson = (Map<String, Object>) rs.getObject("data");

                SkillData skill = new SkillData(id, skillId, skillName, level, dataJson);
                userData.loadSkill(skill);
            }
        }
    }

    public void loadUserAchievemnts(UserData userData) throws SQLException {
        try (Connection c = db.getConnection()) {
            PreparedStatement st = c.prepareStatement(
                "SELECT ua.achievement_key AS `key`, ua.progress_count AS `count` " +
                "FROM user_achievements ua " +
                "WHERE ua.user_id = ?"
            );
            st.setString(1, userData.getId().toString());

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String key = rs.getString("key");
                int count = rs.getInt("count");
                System.out.println(key);
                System.out.println(count);
                AchievementUserData achievementUserData = userData.addAchievement(key);
                achievementUserData.setProgress(count);
                achievementUserData.setNew(false);
            }
        }
    }

    public UserData createUser(UUID id, String nickname) throws SQLException {
        try (Connection c = db.getConnection()) {
            PreparedStatement st = c.prepareStatement(
                "INSERT INTO users (id, nickname) VALUES (?, ?)"
            );
            st.setString(1, id.toString());
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
                    insert.setString(1, userData.getId().toString());
                    insert.setString(2, skill.getSkillId().toString());
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
                    update.setString(3, userData.getId().toString());
                    update.setString(4, skill.getSkillId().toString());
                    update.executeUpdate();
                    skill.setModified(false);
                }
            }
        }
    }

    public void saveUserAchievements(UserData userData) throws SQLException {
        try (Connection c = db.getConnection()) {
            for (AchievementUserData achievement : userData.getAchievements().values()) {
                if (achievement.isNew()) {
                    PreparedStatement insert = c.prepareStatement(
                        "INSERT INTO user_achievements (user_id, achievement_key, progress_count) VALUES (?, ?, ?)"
                    );
                    insert.setString(1, userData.getId().toString());
                    insert.setString(2, achievement.getKey());
                    insert.setInt(3, achievement.getProgress());
                    insert.executeUpdate();
                    achievement.setNew(false);
                    continue;
                }

                if (achievement.isModified()) {
                    PreparedStatement update = c.prepareStatement(
                        "UPDATE user_achievements SET progress_count = ? WHERE user_id = ? AND achievement_key = ?"
                    );
                    update.setInt(1, achievement.getProgress());
                    update.setString(2, userData.getId().toString());
                    update.setString(3, achievement.getKey());
                    update.executeUpdate();
                    achievement.setModified(false);
                }
            }
        }
    }

    public void saveUser(UserData userData) throws SQLException {
        System.out.println(userData.toString());
        try (Connection c = db.getConnection()) {

            PreparedStatement st = c.prepareStatement(
                "UPDATE users SET nickname = ?, level = ?, exp = ? WHERE id = ?"
            );

            st.setString(1, userData.getNickName());
            st.setInt(2, userData.getLevel());
            st.setInt(3, userData.getExp());
            st.setString(4, userData.getId().toString());

            st.executeUpdate();
        }
    }
}
