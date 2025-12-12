package org.dev.nebula.core.spawn.drops.frozen;

import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.dev.nebula.core.items.weapons.SimpleSword;
import org.dev.nebula.core.services.ItemsService;
import org.dev.nebula.core.spawn.interfaces.MobDrop;

public class FrozenSwordDrop extends MobDrop{
    @Override
    public double getChance(EntityDeathEvent event) {
        return 1;
    }
    @Override
    public ItemStack getItem(EntityDeathEvent event) {
        return ItemsService.items.get(SimpleSword.ITEM_NAME).createItemStack(4);
    }
}
