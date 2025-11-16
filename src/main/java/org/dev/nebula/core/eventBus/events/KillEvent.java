package org.dev.nebula.core.eventBus.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class KillEvent {
    public final Player killer;
    public final LivingEntity victim;

    public KillEvent(Player killer, LivingEntity victim) {
        this.killer = killer;
        this.victim = victim;
    }
}