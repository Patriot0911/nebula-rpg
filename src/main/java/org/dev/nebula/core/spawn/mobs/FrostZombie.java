package org.dev.nebula.core.spawn.mobs;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.dev.nebula.core.spawn.interfaces.CustomMob;

public class FrostZombie implements CustomMob {
    @Override
    public EntityType getEntityType() {
        return EntityType.ZOMBIE;
    }

    @Override
    public void modifyEntity(LivingEntity entity) {
        Zombie z = (Zombie) entity;
        z.setCustomName("§bFrost Zombie");
        z.setCustomNameVisible(true);
        z.getEquipment().setHelmet(new ItemStack(Material.ICE));
        z.getEquipment().setHelmetDropChance(0f);
    }
}

