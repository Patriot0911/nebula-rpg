package org.dev.nebula.core.achievements.implementation;

import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.dev.nebula.core.achievements.AchievementBase;
import org.dev.nebula.core.db.models.SkillData;
import org.dev.nebula.core.db.models.UserData;
import org.dev.nebula.core.events.EventBus;
import org.dev.nebula.core.events.busEvents.playerDeath.PlayerKillEvent;
import org.dev.nebula.core.services.MobsService;
import org.dev.nebula.core.services.UsersService;
import org.dev.nebula.core.skills.PassiveSkillBase;
import org.dev.nebula.core.skills.passive.IceLandAuthorityPasive;
import org.dev.nebula.core.skills.passive.RottenFoodPasive;
import org.dev.nebula.core.spawn.mobs.frozen.FrozenKnight;

import net.kyori.adventure.text.Component;

public class KilledIceKnights extends AchievementBase {
    public static final String KEY = "killed_ice_knigts";
    public static final int GOAL = 20;

    public KilledIceKnights(EventBus eventBus) {
        super(eventBus);

        eventBus.subscribe(PlayerKillEvent.class, this::onPlayerKill);
    }

    private void onPlayerKill(PlayerKillEvent e) {
        if (!(e.event.getEntity() instanceof Mob entity)) return;
        if (!(e.event.getEntity().getKiller() instanceof Player player)) return;
        UserData userData = UsersService.getUserData(player.getUniqueId());
        if (isAchieved(player.getUniqueId())) return;

        var persistenceDataContainer = entity.getPersistentDataContainer();
        String mobId = persistenceDataContainer.get(MobsService.MOB_ID, PersistentDataType.STRING);
        if (mobId == null || !mobId.equals(FrozenKnight.MOB_ID)) return;

        addAchievementProgress(player.getUniqueId(), 1);
        int progress = getAchievementUserProgress(player.getUniqueId());

        if (progress >= getGoal() && !userData.hasSkill(IceLandAuthorityPasive.SKILL_NAME)) {
            SkillData skillData = new SkillData(
                PassiveSkillBase.skillNameToUUID(IceLandAuthorityPasive.SKILL_NAME),
                player.getUniqueId(),
                RottenFoodPasive.SKILL_NAME,
                1,
                null
            );

            userData.addSkill(skillData);
        }
    }

    @Override
    public Component getName() {
        return Component.text("Ice Land Lord");
    }

    @Override
    public int getGoal() {
        return KilledIceKnights.GOAL;
    }
    @Override
    public String getKey() {
        return KilledIceKnights.KEY;
    }
}
