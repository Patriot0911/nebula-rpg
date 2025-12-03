package org.dev.nebula.core.crafts;

import org.bukkit.entity.Player;
import org.bukkit.inventory.CraftingInventory;
import org.dev.nebula.core.services.UserService;

public abstract class CraftCondition {
    protected UserService userService;

    public CraftCondition(UserService userService) {
        this.userService = userService;
    }

    public abstract boolean canCraft(Player player, CraftingInventory inv);
    public abstract String getErrorMessage();
}
