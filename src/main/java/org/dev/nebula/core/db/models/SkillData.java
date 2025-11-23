package org.dev.nebula.core.db.models;

import java.util.Map;
import java.util.UUID;

public class SkillData {

    private final UUID id;
    private final String name;
    private int level = 1;
    private Map<String, Object> data;

    private boolean isNew = false;
    private boolean isModified = false;

    public SkillData(UUID id, String name, int level, Map<String, Object> data) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.data = data;
    }

    public UUID getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getLevel() {
        return level;
    }

    public boolean isNew() {
        return isNew;
    }
    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public boolean isModified() {
        return isModified;
    }
    public void setModified(boolean isModified) {
        this.isModified = isModified;
    }

    public Map<String, Object> getData() {
        return data;
    }
    public Object getDataValue(String key) {
        return data.get(key);
    }
}