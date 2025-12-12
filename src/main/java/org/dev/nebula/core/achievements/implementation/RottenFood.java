package org.dev.nebula.core.achievements.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.dev.nebula.core.achievements.AchievementBase;
import org.dev.nebula.core.db.models.AchievementUserData;
import org.dev.nebula.core.db.models.SkillData;
import org.dev.nebula.core.db.models.UserData;
import org.dev.nebula.core.events.EventBus;
import org.dev.nebula.core.events.busEvents.items.PlayerItemConsumeBusEvent;
import org.dev.nebula.core.services.UsersService;
import org.dev.nebula.core.skills.PassiveSkillBase;
import org.dev.nebula.core.skills.passive.RottenFoodPasive;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class RottenFood extends AchievementBase {
    public static final String KEY = "rotten_food";
    public static final int GOAL = 20;

    private final int IMUNE_PROGRESS_POINT = 5;

    public final int[] progressActions = new int[] {
        IMUNE_PROGRESS_POINT,
    };

    public RottenFood(EventBus eventBus) {
        super(eventBus);

        eventBus.subscribe(PlayerItemConsumeBusEvent.class, this::onPlayerConsume);
    }

    public void onPlayerConsume(PlayerItemConsumeBusEvent e) {
        UUID playerId = e.event.getPlayer().getUniqueId();
        if (e.event.getItem().getType() == Material.ROTTEN_FLESH) {
            int progressCount = getAchievementUserProgress(playerId);
            UserData userData = UsersService.getUserData(playerId);
            if (!isAchieved(playerId)) {
                addAchievementProgress(playerId, 1);
                progressCount += 1;
            }
            if (
                progressCount >= progressActions[0] &&
                !userData.hasSkill(RottenFoodPasive.SKILL_NAME)
            ) {
                SkillData rottenSkillData = new SkillData(
                    PassiveSkillBase.skillNameToUUID(RottenFoodPasive.SKILL_NAME),
                    playerId,
                    RottenFoodPasive.SKILL_NAME,
                    1,
                    null
                );
                userData.addSkill(rottenSkillData);
                e.event.getPlayer().sendActionBar(
                    Component
                        .text("Skill \"Rotten Food immuntiy\" recieved")
                        .color(TextColor.color(255, 20, 150))
                );
            }
        }
    }

    @Override
    public List<Component> getDescription(UUID userId) {
        List<Component> description = new ArrayList<Component>();
        UserData userData = UsersService.getUserData(userId);
        AchievementUserData achievementUserData = userData.getAchievementUserData(KEY);
        if (achievementUserData.getProgress() >= progressActions[0]) {
            description.add(
                Component
                    .text("[Achieved] Rotten Food immuntiy (I)")
                    .color(TextColor.color(121, 212, 91))
            );
        }
        return description;
    }
    @Override
    public Component getName() {
        return Component.text("Undead Consumer");
    }

    @Override
    public int getGoal() {
        return RottenFood.GOAL;
    }
    @Override
    public String getKey() {
        return RottenFood.KEY;
    }
}
