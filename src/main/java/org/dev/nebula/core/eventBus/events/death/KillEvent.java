package org.dev.nebula.core.eventBus.events.death;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.dev.nebula.core.eventBus.PlayerBusEventBase;

public class KillEvent extends PlayerBusEventBase<EntityDeathEvent> {
    public final LivingEntity victim;

    public KillEvent(EntityDeathEvent event, Player killer, LivingEntity victim) {
        super(killer, event);
        this.victim = victim;
    }
}
