package org.dev.nebula.core.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.dev.nebula.core.db.DaoBase;
import org.dev.nebula.core.db.DatabaseManager;

public class SkillDao extends DaoBase {
    public SkillDao(DatabaseManager databaseManager) {
        super(databaseManager);
    }

    public void syncSkills(Set<String> passiveSkills) throws SQLException {
        try (Connection c = db.getConnection()) {
            c.setAutoCommit(false);

            Map<String, UUID> currentSkills = new HashMap<>();
            for (String skillName : passiveSkills) {
                UUID id = UUID.nameUUIDFromBytes(skillName.getBytes());
                currentSkills.put(skillName, id);
            }

            String insertSql = "INSERT INTO skills (id, name) VALUES (?, ?) ON CONFLICT (id) DO NOTHING";
            try (PreparedStatement ps = c.prepareStatement(insertSql)) {
                for (Map.Entry<String, UUID> entry : currentSkills.entrySet()) {
                    ps.setObject(1, entry.getValue());
                    ps.setString(2, entry.getKey());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            String deleteSql = "DELETE FROM skills WHERE name NOT IN (" +
                    currentSkills.keySet().stream().map(s -> "?").collect(Collectors.joining(",")) +
                    ")";
            try (PreparedStatement ps = c.prepareStatement(deleteSql)) {
                int i = 1;
                for (String name : currentSkills.keySet()) {
                    ps.setString(i++, name);
                }
                ps.executeUpdate();
            }

            c.commit();
        }
    }
}
