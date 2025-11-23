package org.dev.nebula.core.skills.passive;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.dev.nebula.core.eventBus.EventBus;
import org.dev.nebula.core.eventBus.events.KillEvent;
import org.dev.nebula.core.services.UserService;
import org.dev.nebula.core.skills.PassiveSkillBase;
import org.bukkit.Particle;

public class LifeStealPassive extends PassiveSkillBase {

    public static final String SKILL_NAME = "lifeSteal";

    public LifeStealPassive(EventBus bus, UserService userService) {
        super(bus, userService);
        bus.subscribe(KillEvent.class, this::onKill);
    }

    public void onKill(KillEvent e) {
        if(e.killer.getAttribute(Attribute.MAX_HEALTH) == null) return;

        System.out.println("Tested");

        double maxHealth = e.killer.getAttribute(Attribute.MAX_HEALTH).getValue();
        Location loc = e.killer.getLocation().add(0, 1, 0);

        for (double i = -1.0; i <= 1.0; i += 0.25) {
            e.killer.spawnParticle(
                    Particle.DUST,
                    loc.clone().add(0, i, 0),
                    1,
                    new Particle.DustOptions(Color.BLUE, 2.0f)
            );
        }
        e.killer.setHealth(Math.min(maxHealth, e.killer.getHealth() + 4));
    }
}
