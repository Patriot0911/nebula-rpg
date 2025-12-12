package org.dev.nebula.core.spawn.interfaces;

import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public abstract class MobDrop {
    public abstract ItemStack getItem(EntityDeathEvent event);
    public abstract double getChance(EntityDeathEvent event);
}
