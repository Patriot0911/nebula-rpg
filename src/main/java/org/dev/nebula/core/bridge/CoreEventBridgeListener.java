package org.dev.nebula.core.bridge;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.dev.nebula.core.eventBus.EventBus;
import org.dev.nebula.core.eventBus.events.DamageEvent;
import org.dev.nebula.core.eventBus.events.KillEvent;

public class CoreEventBridgeListener implements Listener {

    private final EventBus eventBus;

    public CoreEventBridgeListener(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        var killer = event.getEntity().getKiller();
        if (killer != null)
            eventBus.publish(new KillEvent(killer, event.getEntity()));
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player attacker &&
                event.getEntity() instanceof LivingEntity target) {

            DamageEvent de = new DamageEvent(attacker, target, event.getDamage());
            eventBus.publish(de);
            event.setDamage(de.damage);
        }
    }
}

