package org.dev.nebula.core.items;

import java.net.http.WebSocket.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class ItemListener implements Listener{

    public ItemListener() {}

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
    }
    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
    }
    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
    }
}
