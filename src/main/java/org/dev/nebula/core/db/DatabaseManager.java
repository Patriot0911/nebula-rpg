package org.dev.nebula.core.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {

    private HikariDataSource dataSource;

    public void connect(DatabaseConfig cfg) {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(cfg.toJdbcUrl());
        config.setUsername(cfg.getUsername());
        config.setPassword(cfg.getPassword());

        config.setMaximumPoolSize(cfg.getMaxPoolSize());
        config.setConnectionTimeout(cfg.getConnectionTimeout());
        config.setMinimumIdle(2);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
