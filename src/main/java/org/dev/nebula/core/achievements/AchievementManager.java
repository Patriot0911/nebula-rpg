package org.dev.nebula.core.achievements;

import org.dev.nebula.core.events.EventBus;
import org.dev.nebula.core.services.AchievementsService;
import org.dev.nebula.core.services.UsersService;

public class AchievementManager {
    private EventBus eventBus;
    private UsersService userService;
    private AchievementsService achievementsService;

    public AchievementManager(EventBus eventBus, UsersService userService, AchievementsService achievementsService) {
        this.eventBus = eventBus;
        this.userService = userService;
        this.achievementsService = achievementsService;
    }

    public void registerAchievements() {
        for (Class<? extends AchievementBase> achievementClass : achievementsService.getAchievementsList()) {
            try {
                AchievementBase achievement = achievementClass
                    .getDeclaredConstructor(EventBus.class, UsersService.class)
                    .newInstance(eventBus, userService);
                achievementsService.registerAchievement(achievement.getKey(), achievement);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Cannot load achievement: " + achievementClass.getName());
            }
        }
    }
}
