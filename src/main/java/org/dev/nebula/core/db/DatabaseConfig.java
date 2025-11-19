package org.dev.nebula.core.db;

import org.bukkit.configuration.file.YamlConfiguration;

public class DatabaseConfig {

    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final String password;

    private final int maxPoolSize;
    private final long connectionTimeout;

    public DatabaseConfig(YamlConfiguration config) {
        this.host = config.getString("database.host");
        this.port = config.getInt("database.port");
        this.database = config.getString("database.database");
        this.username = config.getString("database.username");
        this.password = config.getString("database.password");

        this.maxPoolSize = config.getInt("database.maxPoolSize");
        this.connectionTimeout = config.getLong("database.connectionTimeout");
    }

    public String toJdbcUrl() {
        return "jdbc:postgresql://" + host + ":" + port + "/" + database;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public int getMaxPoolSize() {
        return maxPoolSize;
    }
    public long getConnectionTimeout() {
        return connectionTimeout;
    }
}
