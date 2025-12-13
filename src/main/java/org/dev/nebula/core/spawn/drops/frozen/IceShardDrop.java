package org.dev.nebula.core.spawn.drops.frozen;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.dev.nebula.core.db.models.UserData;
import org.dev.nebula.core.items.shards.IceShard;
import org.dev.nebula.core.services.ItemsService;
import org.dev.nebula.core.services.UsersService;
import org.dev.nebula.core.skills.passive.IceLandAuthorityPasive;
import org.dev.nebula.core.spawn.interfaces.MobDrop;

public class IceShardDrop extends MobDrop{
    @Override
    public double getChance(EntityDeathEvent event) {
        double chance = 0.3;
        if (event.getEntity().getKiller() instanceof Player player) {
            UserData userData = UsersService.getUserData(player.getUniqueId());
            if (userData.hasSkill(IceLandAuthorityPasive.SKILL_NAME)) {
                chance = 0.8;
            }
        }
        return chance;
    }
    @Override
    public ItemStack getItem(EntityDeathEvent event) {
        int min = 1;
        if (event.getEntity().getKiller() instanceof Player player) {
            UserData userData = UsersService.getUserData(player.getUniqueId());
            if (userData.hasSkill(IceLandAuthorityPasive.SKILL_NAME)) {
                min += (int)(Math.random()*3);
            }
        }
        return ItemsService
            .getItem(IceShard.ITEM_NAME)
            .createItemStack(
                min + (int)(Math.random()*3)
            );
    }
}
