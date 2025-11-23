package org.dev.nebula.core.menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class MenuBase implements InventoryHolder {
    protected final Player player;

    public MenuBase(Player player) {
        this.player = player;
    }

    public abstract Inventory getInventory();

    public abstract void onClick(InventoryClickEvent event);

    public void open() {
        player.openInventory(getInventory());
    }
    public boolean isEditable() {
        return false;
    }

    protected Inventory createInventory(InventoryHolder holder, InventoryType type) {
        return Bukkit.createInventory(holder, type);
    }
    @SuppressWarnings("deprecation")
    protected Inventory createInventory(InventoryHolder holder, int size, String title) {
        return Bukkit.createInventory(holder, size, title);
    }
}
