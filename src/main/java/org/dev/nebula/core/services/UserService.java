package org.dev.nebula.core.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.dev.nebula.core.db.dao.UserDao;
import org.dev.nebula.core.db.models.UserData;

public class UserService {
    private final UserDao userDao;
    private final Map<String, UserData> cache = new ConcurrentHashMap<>();

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
}
