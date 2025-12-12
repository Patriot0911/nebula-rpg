package org.dev.nebula.core.menus;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuHolder implements InventoryHolder {
    private final MenuBuilder menu;

    public MenuHolder(MenuBuilder menu) {
        this.menu = menu;
    }

    @Override
    public Inventory getInventory() {
        return menu.getInventory();
    }

    public MenuBuilder getMenu() {
        return menu;
    }
}
