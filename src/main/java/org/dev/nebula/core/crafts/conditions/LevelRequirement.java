package org.dev.nebula.core.crafts.conditions;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.inventory.CraftingInventory;
import org.dev.nebula.core.crafts.CraftCondition;
import org.dev.nebula.core.db.models.UserData;
import org.dev.nebula.core.services.UserService;

public class LevelRequirement extends CraftCondition {
    private final int level;

    public LevelRequirement(UserService userService, int level) {
        super(userService);
        this.level = level;
    }

    @Override
    public boolean canCraft(Player player, CraftingInventory inv) {
        UUID plaUuid = player.getUniqueId();
        UserData userData = userService.getUserData(plaUuid);
        if (userData == null) return false;
        return userData.getLevel() >= level;
    }

    @Override
    public String getErrorMessage() {
        return String.format("Вам потрібно мати {0} рівень ", level);
    }
}