package org.dev.nebula.core.menus;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof MenuBase menu)) {
            return;
        }

        event.setCancelled(!menu.isEditable());

        menu.onClick(event);
    }
}
