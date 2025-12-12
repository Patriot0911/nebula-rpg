package org.dev.nebula.core.skills.passive;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffectType;
import org.dev.nebula.core.db.models.UserData;
import org.dev.nebula.core.events.EventBus;
import org.dev.nebula.core.events.busEvents.PotionEffectEvent;
import org.dev.nebula.core.services.UsersService;
import org.dev.nebula.core.skills.PassiveSkillBase;

public class RottenFoodPasive extends PassiveSkillBase {
    public static final String SKILL_NAME = "rootenFood";

    public RottenFoodPasive(EventBus bus) {
        super(bus);
        bus.subscribe(PotionEffectEvent.class, this::onEffect);
    }

    public void onEffect(PotionEffectEvent e) {
        if (!(e.event.getEntity() instanceof Player player)) return;
        if (e.event.getNewEffect() == null) return;

        UserData userData = UsersService.getUserData(player.getUniqueId());
        if(userData == null || !userData.hasSkill(SKILL_NAME)) return;

        if (
            e.event.getNewEffect().getType() == PotionEffectType.HUNGER &&
            e.event.getCause() == EntityPotionEffectEvent.Cause.FOOD
        ) return;
        e.event.setCancelled(true);
    }
}
