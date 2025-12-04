package org.dev.nebula.core.spawn.interfaces;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public interface CustomMob {
    EntityType getEntityType();

    void modifyEntity(LivingEntity entity);
}
