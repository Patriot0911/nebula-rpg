package org.dev.nebula.core.events.busEvents.playerDeath;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.dev.nebula.core.events.bases.PlayerBusEventBase;

public class PlayerKillEvent extends PlayerBusEventBase<EntityDeathEvent> {
    public final LivingEntity victim;

    public PlayerKillEvent(EntityDeathEvent event, Player killer, LivingEntity victim) {
        super(killer, event);
        this.victim = victim;
    }
}
