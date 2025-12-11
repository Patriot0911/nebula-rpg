package org.dev.nebula.core.bridge;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.dev.nebula.core.events.EventBus;
import org.dev.nebula.core.events.busEvents.PotionEffectEvent;
import org.dev.nebula.core.events.busEvents.damage.DamageGiveEvent;
import org.dev.nebula.core.events.busEvents.damage.DamagePostEvent;
import org.dev.nebula.core.events.busEvents.damage.DamageTakeEvent;
import org.dev.nebula.core.events.busEvents.items.CraftItemEventBusEvent;
import org.dev.nebula.core.events.busEvents.items.EntityPickupItemBusEvent;
import org.dev.nebula.core.events.busEvents.items.InventoryClickBusEvent;
import org.dev.nebula.core.events.busEvents.items.ItemBreakBusEvent;
import org.dev.nebula.core.events.busEvents.items.PlayerDropItemBusEvent;
import org.dev.nebula.core.events.busEvents.items.PlayerInteractBusEvent;
import org.dev.nebula.core.events.busEvents.items.PlayerItemConsumeBusEvent;
import org.dev.nebula.core.events.busEvents.playerDeath.PlayerDeathEvent;
import org.dev.nebula.core.events.busEvents.playerDeath.PlayerKillEvent;

public class CoreEventBridgeListener implements Listener {

    private final EventBus eventBus;

    public CoreEventBridgeListener(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        var killer = event.getEntity().getKiller();
        if(killer != null && killer instanceof Player) {
            eventBus.publish(new PlayerKillEvent(event, killer, event.getEntity()));
        }
        if(event.getEntity() != null && event.getEntity() instanceof Player victim) {
            eventBus.publish(new PlayerDeathEvent(event, victim));
        }
    }

    @EventHandler
    public void onEntityPotionEffect(EntityPotionEffectEvent event) {
        PotionEffectEvent potionEffect = new PotionEffectEvent(event);
        eventBus.publish(potionEffect);
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        PlayerInteractBusEvent interactEvent = new PlayerInteractBusEvent(event);
        eventBus.publish(interactEvent);
    }
    @EventHandler
    public void PlayerDropItem(PlayerDropItemEvent event) {
        PlayerDropItemBusEvent dropEvent = new PlayerDropItemBusEvent(event);
        eventBus.publish(dropEvent);
    }
    @EventHandler
    public void onEntityPickup(EntityPickupItemEvent event) {
        EntityPickupItemBusEvent pickupEvent = new EntityPickupItemBusEvent(event);
        eventBus.publish(pickupEvent);
    }
    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        PlayerItemConsumeBusEvent consumeBusEvent = new PlayerItemConsumeBusEvent(event);
        eventBus.publish(consumeBusEvent);
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryClickBusEvent consumeBusEvent = new InventoryClickBusEvent(event);
        eventBus.publish(consumeBusEvent);
    }
    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        CraftItemEventBusEvent consumeBusEvent = new CraftItemEventBusEvent(event);
        eventBus.publish(consumeBusEvent);
    }
    @EventHandler
    public void onBreak(PlayerItemBreakEvent event) {
        ItemBreakBusEvent breakBusEvent = new ItemBreakBusEvent(event);
        eventBus.publish(breakBusEvent);
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

