package org.dev.nebula.core.mobs.custom;

import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.dev.nebula.core.mobs.CustomMob;

public class FrostZombie implements CustomMob {

    @Override
    public Set<Biome> getAllowedBiomes() {
        return Set.of(
                Biome.SNOWY_PLAINS,
                Biome.SNOWY_TAIGA,
                Biome.ICE_SPIKES
        );
    }

    @Override
    public double getSpawnChance() {
        return 0.25;
    }

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

