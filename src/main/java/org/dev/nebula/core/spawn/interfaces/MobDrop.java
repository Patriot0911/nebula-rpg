package org.dev.nebula.core.spawn.interfaces;

import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.dev.nebula.core.services.ItemsService;
import org.dev.nebula.core.services.UsersService;

public abstract class MobDrop {
    protected UsersService usersService;
    protected ItemsService itemsService;

    public MobDrop(UsersService usersService, ItemsService itemsService) {
        this.usersService = usersService;
        this.itemsService = itemsService;
    }

    public abstract ItemStack getItem(EntityDeathEvent event);
    public abstract double getChance(EntityDeathEvent event);
}
