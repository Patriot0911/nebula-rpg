package org.dev.nebula.core.achievements;

import java.util.UUID;

import org.dev.nebula.core.db.models.AchievementUserData;
import org.dev.nebula.core.db.models.UserData;
import org.dev.nebula.core.events.EventBus;
import org.dev.nebula.core.services.UsersService;

public abstract class AchievementBase {
    protected EventBus eventBus;

    public AchievementBase(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public abstract String getKey();
    public abstract int getGoal();

    public void addAchievementProgress(UUID userId, int count) {
        UserData userData = UsersService.getUserData(userId);
        if (userData == null) return;
        AchievementUserData achievementUserData = userData.getAchievementUserData(getKey());
        if (achievementUserData == null) {
            achievementUserData = userData.addAchievement(getKey());
            achievementUserData.setNew(false);
            achievementUserData.addProgress(count, getGoal());
            return;
        }
        achievementUserData.addProgress(count, getGoal());
    }
    public int getAchievementUserProgress(UUID userId) {
        UserData userData = UsersService.getUserData(userId);
        if (userData == null) return 0;
        AchievementUserData achievementUserData = userData.getAchievementUserData(getKey());
        if (achievementUserData == null) return 0;
        return achievementUserData.getProgress();
    }
    public boolean isAchieved(UUID userId) {
        UserData userData = UsersService.getUserData(userId);
        if (userData == null) return false;
        AchievementUserData achievementUserData = userData.getAchievementUserData(getKey());
        if (achievementUserData == null) return false;
        return achievementUserData.getProgress() == getGoal();
    }
}
