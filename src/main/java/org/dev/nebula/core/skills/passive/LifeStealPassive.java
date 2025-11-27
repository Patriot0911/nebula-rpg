package org.dev.nebula.core.skills.passive;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.dev.nebula.core.db.models.UserData;
import org.dev.nebula.core.eventBus.EventBus;
import org.dev.nebula.core.eventBus.events.death.KillEvent;
import org.dev.nebula.core.services.UserService;
import org.dev.nebula.core.skills.PassiveSkillBase;
import org.bukkit.Particle;

public class LifeStealPassive extends PassiveSkillBase {

    public static final String SKILL_NAME = "lifeSteal";

    public LifeStealPassive(EventBus bus, UserService userService) {
        super(bus, userService);
        subscribeSkillEvent(KillEvent.class, this::onKill, SKILL_NAME);
    }

    public void onKill(KillEvent e, UserData userData) {
        Player killer = e.getProducer();
        if(killer.getAttribute(Attribute.MAX_HEALTH) == null) return;

        double maxHealth = killer.getAttribute(Attribute.MAX_HEALTH).getValue();
        Location loc = killer.getLocation().add(0, 1, 0);

        for (double i = -1.0; i <= 1.0; i += 0.25) {
            killer.spawnParticle(
                    Particle.DUST,
                    loc.clone().add(0, i, 0),
                    1,
                    new Particle.DustOptions(Color.BLUE, 2.0f)
            );
        }
        killer.setHealth(Math.min(maxHealth, killer.getHealth() + 4));
    }
}
