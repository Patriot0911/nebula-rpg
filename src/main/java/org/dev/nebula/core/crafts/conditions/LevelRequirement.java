package org.dev.nebula.core.crafts.conditions;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.inventory.CraftingInventory;
import org.dev.nebula.core.crafts.CraftCondition;
import org.dev.nebula.core.db.models.UserData;
import org.dev.nebula.core.services.UsersService;

public class LevelRequirement extends CraftCondition {
    private final int level;

    public LevelRequirement(int level) {
        this.level = level;
    }

    @Override
    public boolean canCraft(Player player, CraftingInventory inv) {
        UUID plaUuid = player.getUniqueId();
        UserData userData = UsersService.getUserData(plaUuid);
        if (userData == null) return false;
        return userData.getLevel() >= level;
    }
}