package org.dev.nebula.core.events.busEvents.damage;

import org.bukkit.entity.LivingEntity;

public class DamagePostEvent {
    public final LivingEntity attacker;
    public final LivingEntity target;
    public double damage;
    public DamagePostEvent(LivingEntity attacker, LivingEntity target, double damage) {
        this.target = target;
        this.attacker = attacker;
        this.damage = damage;
    }
}
