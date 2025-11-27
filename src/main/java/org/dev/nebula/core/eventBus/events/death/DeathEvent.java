package org.dev.nebula.core.eventBus.events.death;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.dev.nebula.core.eventBus.PlayerBusEventBase;

public class DeathEvent extends PlayerBusEventBase<EntityDeathEvent> {
    public DeathEvent(EntityDeathEvent event, Player victim) {
        super(victim, event);
    }
}
