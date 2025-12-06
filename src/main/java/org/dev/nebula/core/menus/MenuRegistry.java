package org.dev.nebula.core.menus;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MenuRegistry implements Listener {
    private static final Map<UUID, MenuBuilder> OPEN_MENUS = new HashMap<>();

    public static void register(Player p, MenuBuilder menu) {
        OPEN_MENUS.put(p.getUniqueId(), menu);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player p)) return;

        MenuBuilder menu = OPEN_MENUS.get(p.getUniqueId());
        if (menu == null) return;

        e.setCancelled(true);
        menu.handleClick(e);
    }
}
