package org.dev.nebula.core.events.busEvents.playerDeath;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.dev.nebula.core.events.bases.PlayerBusEventBase;

public class PlayerDeathEvent extends PlayerBusEventBase<EntityDeathEvent> {
    public PlayerDeathEvent(EntityDeathEvent event, Player victim) {
        super(victim, event);
    }
}
