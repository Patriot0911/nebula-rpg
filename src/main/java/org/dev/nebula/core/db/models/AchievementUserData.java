package org.dev.nebula.core.db.models;

public class AchievementUserData {
    private final String key;

    private int progressCount = 0;

    private boolean isNew = false;
    private boolean isModified = false;

    public AchievementUserData(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
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

    public int setProgress(int count) {
        this.progressCount = count;
        if (!isNew && !isModified) {
            setModified(true);
        }
        return this.progressCount;
    }
    public int addProgress(int count, int limit) {
        this.progressCount = Math.min(limit, this.progressCount+count);
        if (!isNew && !isModified) {
            setModified(true);
        }
        return this.progressCount;
    }
    public int subtractProgress(int count) {
        this.progressCount = Math.max(this.progressCount - count, 0);
        if (!isNew && !isModified) {
            setModified(true);
        }
        return this.progressCount;
    }
    public int getProgress() {
        return this.progressCount;
    }
}