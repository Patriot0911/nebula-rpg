package org.dev.nebula.core.eventBus.events.damage;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.dev.nebula.core.eventBus.PlayerBusEventBase;

public class DamageGiveEvent extends PlayerBusEventBase<EntityDamageByEntityEvent> {
    public final LivingEntity target;
    public double damage;
    public DamageGiveEvent(EntityDamageByEntityEvent event, Player attacker, LivingEntity target, double damage) {
        super(attacker, event);
        this.target = target;
        this.damage = damage;
    }
}
