package org.dev.nebula.core.crafts;

import org.bukkit.entity.Player;
import org.bukkit.inventory.CraftingInventory;

public abstract class CraftCondition {
    public abstract boolean canCraft(Player player, CraftingInventory inv);
}
