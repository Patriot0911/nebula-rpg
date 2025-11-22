package org.dev.nebula.core.db;

import org.flywaydb.core.Flyway;

public class DatabaseMigrator {

    public static void migrate(DatabaseConfig cfg, ClassLoader classLoader) {

        Flyway flyway = Flyway.configure(classLoader)
                .dataSource(cfg.toJdbcUrl(), cfg.getUsername(), cfg.getPassword())
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .load();

        flyway.migrate();
    }
}
