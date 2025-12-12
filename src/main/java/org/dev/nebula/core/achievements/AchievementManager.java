package org.dev.nebula.core.achievements;

import org.dev.nebula.core.events.EventBus;
import org.dev.nebula.core.services.AchievementsService;

public class AchievementManager {
    private EventBus eventBus;
    private AchievementsService achievementsService;

    public AchievementManager(EventBus eventBus, AchievementsService achievementsService) {
        this.eventBus = eventBus;
        this.achievementsService = achievementsService;
    }

    public void registerAchievements() {
        for (Class<? extends AchievementBase> achievementClass : achievementsService.getAchievementsList()) {
            try {
                AchievementBase achievement = achievementClass
                    .getDeclaredConstructor(EventBus.class)
                    .newInstance(eventBus);
                achievementsService.registerAchievement(achievement.getKey(), achievement);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Cannot load achievement: " + achievementClass.getName());
            }
        }
    }
}
