package org.dev.nebula.core.spawn.drops.frozen;

import org.bukkit.Material;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.dev.nebula.core.services.ItemsService;
import org.dev.nebula.core.services.UsersService;
import org.dev.nebula.core.spawn.interfaces.MobDrop;

public class FrozenBlockDrop extends MobDrop{
    public FrozenBlockDrop(UsersService usersService, ItemsService itemsService) {
        super(usersService, itemsService);
    }

    @Override
    public double getChance(EntityDeathEvent event) {
        return 0.5;
    }
    @Override
    public ItemStack getItem(EntityDeathEvent event) {
        return new ItemStack(Material.ICE, 24);
    }
}
