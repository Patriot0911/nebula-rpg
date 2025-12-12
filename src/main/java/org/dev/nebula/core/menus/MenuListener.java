package org.dev.nebula.core.menus;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;

        if (e.getInventory().getHolder() instanceof MenuHolder holder) {
            e.setCancelled(true);
            holder.getMenu().handleClick(e);
        }
    }
}
