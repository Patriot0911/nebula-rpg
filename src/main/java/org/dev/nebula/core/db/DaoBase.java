package org.dev.nebula.core.db;

public abstract class DaoBase {
    protected final DatabaseManager db;

    public DaoBase(DatabaseManager databaseManager) {
        this.db = databaseManager;
    }
}
