package org.dev.nebula.core.services;

import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.dev.nebula.core.db.dao.UserDao;
import org.dev.nebula.core.db.models.UserData;

public class UserService {
    private final UserDao userDao;
    private final Map<UUID, UserData> cache = new ConcurrentHashMap<>();

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserData loadUser(UUID uuid) {
        try {
            return userDao.loadUser(uuid);
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
    public UserData createUser(String nickname) {
        UserData userData = new UserData(UUID.randomUUID(), nickname, 1, 0);
        return userData;
    }

    public UserData getUserData(UUID uuid) {
        return cache.get(uuid);
    }
}
