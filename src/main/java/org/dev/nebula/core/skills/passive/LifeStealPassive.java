package org.dev.nebula.core.skills.passive;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.dev.nebula.core.db.models.UserData;
import org.dev.nebula.core.events.EventBus;
import org.dev.nebula.core.events.busEvents.playerDeath.PlayerKillEvent;
import org.dev.nebula.core.skills.PassiveSkillBase;

public class LifeStealPassive extends PassiveSkillBase {
    public static final String SKILL_NAME = "lifeSteal";

    public LifeStealPassive(EventBus bus) {
        super(bus);
        subscribeSkillEvent(PlayerKillEvent.class, this::onKill, SKILL_NAME);
    }

    public void onKill(PlayerKillEvent e, UserData userData) {
        Player killer = e.getProducer();
        if(killer.getAttribute(Attribute.MAX_HEALTH) == null) return;

        double maxHealth = killer.getAttribute(Attribute.MAX_HEALTH).getValue();

        killer.setHealth(Math.min(maxHealth, killer.getHealth() + (int)(Math.random()*4)));
    }
}
