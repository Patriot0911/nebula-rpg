package org.dev.nebula.core.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dev.nebula.core.achievements.AchievementBase;
import org.dev.nebula.core.achievements.implementation.RottenFood;

public class AchievementsService {
    private final List<Class<? extends AchievementBase>> achievementsList = new ArrayList<Class<? extends AchievementBase>>(
        List.of(
            RottenFood.class
        )
    );
    private final Map<String, AchievementBase> achievements = new HashMap<>();

    public AchievementsService() {}

    public List<Class<? extends AchievementBase>> getAchievementsList() {
        return achievementsList;
    }
    public void registerAchievement(String key, AchievementBase item) {
        achievements.put(key, item);
    }
    public Map<String, AchievementBase> getAchievements() {
        return achievements;
    }
}
