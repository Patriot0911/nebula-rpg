package org.dev.nebula.core.services;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.dev.nebula.core.db.dao.UserDao;
import org.dev.nebula.core.db.models.UserData;

public class UserService {
    private final Plugin plugin;
    private final UserDao userDao;
    private final Map<UUID, UserData> cache = new ConcurrentHashMap<>();

    public UserService(UserDao userDao, Plugin plugin) {
        this.userDao = userDao;
        this.plugin = plugin;
    }

    public UserData loadUser(UUID uuid) {
        try {
            UserData userData = userDao.loadUser(uuid);
            if (userData != null) {
                userDao.loadUserSkills(userData);
            }
            return userData;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void putUserInCache(UserData userData) {
        if (userData != null) {
            cache.put(userData.getId(), userData);
        }
    }
    public UserData createUser(UUID id, String nickname) {
        UserData userData = new UserData(id, nickname, 1, 0);
        Bukkit.getScheduler().runTaskAsynchronously(
            this.plugin,
            () -> {
                try {
                    userDao.createUser(id, nickname);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        );
        return userData;
    }

    public void saveUser(UserData userData) {
        Bukkit.getScheduler().runTaskAsynchronously(
            this.plugin,
            () -> {
                try {
                    userDao.saveUser(userData);
                    userDao.saveUserSkills(userData);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        );
        cache.remove(userData.getId());
    }

    public UserData getUserData(UUID uuid) {
        return cache.get(uuid);
    }
}
