package org.dev.nebula.core.crafts.conditions;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.inventory.CraftingInventory;
import org.dev.nebula.core.crafts.CraftCondition;
import org.dev.nebula.core.db.models.SkillData;
import org.dev.nebula.core.db.models.UserData;
import org.dev.nebula.core.services.UserService;

public class SkillRequirement extends CraftCondition {
    private String skillName;
    private int skillLevel;

    public SkillRequirement(UserService userService, String skillName, int skillLevel) {
        super(userService);
        this.skillName = skillName;
        this.skillLevel = skillLevel;
    }

    @Override
    public boolean canCraft(Player player, CraftingInventory inv) {
        UUID plaUuid = player.getUniqueId();
        UserData userData = userService.getUserData(plaUuid);
        if (userData == null) return false;
        SkillData skillData = userData.getSkill(skillName);
        return skillData != null && skillData.getLevel() >= skillLevel;
    }

    @Override
    public String getErrorMessage() {
        return String.format("Required skill {0} with {1} lvl", skillName, skillLevel);
    }
}
