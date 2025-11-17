package org.dev.nebula.core.mobSpawn;

import java.util.Set;

import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public interface CustomMob {
    Set<Biome> getAllowedBiomes();
    double getSpawnChance(); // 0.0 - 1.0
    EntityType getEntityType();

    void modifyEntity(LivingEntity entity);
}
