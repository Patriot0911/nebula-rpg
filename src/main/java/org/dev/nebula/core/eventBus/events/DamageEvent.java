package org.dev.nebula.core.eventBus.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class DamageEvent {
    public final Player attacker;
    public final LivingEntity target;
    public double damage;
    public DamageEvent(Player attacker, LivingEntity target, double damage) {
        this.attacker = attacker;
        this.target = target;
        this.damage = damage;
    }
}
