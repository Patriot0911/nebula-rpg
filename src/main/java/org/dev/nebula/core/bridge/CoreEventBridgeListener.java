package org.dev.nebula.core.bridge;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.dev.nebula.core.eventBus.EventBus;
import org.dev.nebula.core.eventBus.events.damage.DamageGiveEvent;
import org.dev.nebula.core.eventBus.events.damage.DamagePostEvent;
import org.dev.nebula.core.eventBus.events.damage.DamageTakeEvent;
import org.dev.nebula.core.eventBus.events.death.DeathEvent;
import org.dev.nebula.core.eventBus.events.death.KillEvent;
import org.dev.nebula.core.eventBus.events.items.PlayerInteract;

public class CoreEventBridgeListener implements Listener {

    private final EventBus eventBus;

    public CoreEventBridgeListener(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        var killer = event.getEntity().getKiller();
        if(killer != null && killer instanceof Player) {
            eventBus.publish(new KillEvent(event, killer, event.getEntity()));
        }
        if(event.getEntity() != null && event.getEntity() instanceof Player victim) {
            eventBus.publish(new DeathEvent(event, victim));
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        PlayerInteract playerInteract = new PlayerInteract(event);
        eventBus.publish(playerInteract);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (
            (event.getEntity() instanceof LivingEntity damageTaker && event.getDamager() instanceof LivingEntity damageDealer) &&
            (event.getDamager() instanceof Player || event.getEntity() instanceof Player)
        ) {
            if(damageTaker instanceof Player victim) {
                DamageTakeEvent damageTakeEvent = new DamageTakeEvent(event, victim, damageDealer, event.getDamage());
                eventBus.publish(damageTakeEvent);
                event.setDamage(damageTakeEvent.damage);
            }
            if(damageDealer instanceof Player attacker) {
                DamageGiveEvent damageGiveEvent = new DamageGiveEvent(event, attacker, damageTaker, event.getDamage());
                eventBus.publish(damageGiveEvent);
                event.setDamage(damageGiveEvent.damage);
            }
            DamagePostEvent damagePostEvent = new DamagePostEvent(damageDealer, damageTaker, event.getDamage());
            eventBus.publish(damagePostEvent);
            event.setDamage(damagePostEvent.damage);
        }
    }
}

