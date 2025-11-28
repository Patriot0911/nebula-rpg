package org.dev.nebula.core.events.busEvents.damage;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.dev.nebula.core.events.bases.PlayerBusEventBase;

public class DamageTakeEvent extends PlayerBusEventBase<EntityDamageByEntityEvent> {
    public final LivingEntity attacker;
    public double damage;
    public DamageTakeEvent(EntityDamageByEntityEvent event, Player producer, LivingEntity attacker, double damage) {
        super(producer, event);
        this.attacker = attacker;
        this.damage = damage;
    }
}
