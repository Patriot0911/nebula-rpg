package org.dev.nebula.core.crafts;

import org.bukkit.entity.Player;
import org.bukkit.inventory.CraftingInventory;
import org.dev.nebula.core.services.UsersService;

public abstract class CraftCondition {
    protected UsersService userService;

    public CraftCondition(UsersService userService) {
        this.userService = userService;
    }

    public abstract boolean canCraft(Player player, CraftingInventory inv);
    public abstract String getErrorMessage();
}
