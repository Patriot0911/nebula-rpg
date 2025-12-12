package org.dev.nebula.core.menus;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryCloseEvent.Reason;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MenuRegistry implements Listener {
    private static final Map<UUID, MenuBuilder> OPEN_MENUS = new HashMap<>();

    public static void register(Player p, MenuBuilder menu) {
        System.out.println(menu);
        OPEN_MENUS.put(p.getUniqueId(), menu);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player p)) return;

        MenuBuilder menu = OPEN_MENUS.get(p.getUniqueId());
        System.out.println("TES " + menu);
        if (menu == null) return;

        if (e.getInventory() != p.getOpenInventory().getTopInventory()) return;

        menu.handleClick(e);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player p)) return;

        InventoryCloseEvent.Reason reason = e.getReason();

        if (reason == InventoryCloseEvent.Reason.OPEN_NEW ||
            reason == InventoryCloseEvent.Reason.PLUGIN) {
            return;
        }

        OPEN_MENUS.remove(p.getUniqueId());
    }
}
